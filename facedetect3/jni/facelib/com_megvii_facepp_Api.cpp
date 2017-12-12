#include "MG_Facepp.h"
#include <android/log.h>
#include "com_megvii_facepp_Api.h"
#include <jni.h>

#include <vector>
#include <algorithm>
#include <string>
#include <chrono>
#include <cmath>

#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,"mgf-c",__VA_ARGS__)
template<class point_t>
inline void rotate_point_2d(int w, int h, point_t& x, point_t& y,
		int orientation) {
	point_t tmp;
	switch (orientation % 360) {
	case 90:
		tmp = x;
		x = h - y;
		y = tmp;
		break;
	case 180:
		x = w - x;
		y = h - y;
		break;
	case 270:
		tmp = x;
		x = y;
		y = w - tmp;
		break;
	default:
		break;
	}
}
class Timer {
	std::chrono::system_clock::time_point start;
	std::string name;

public:
	Timer(std::string name) :
			name(name) {
		start = std::chrono::system_clock::now();
	}

	~Timer() {
		std::chrono::system_clock::time_point end =
				std::chrono::system_clock::now();
		LOGE("%s, used time = %lld\n", name.c_str(),
				(std::chrono::duration_cast < std::chrono::microseconds
						> (end - start)).count());
	}
};

#define DETECTION_TRACKING 1
#define DETECTION_NORMAL 0
#define DURATION_30DAYS 30
#define DURATION_365DAYS 365

#define IMAGEMODE_GRAY 0
#define IMAGEMODE_BGR 1
#define IMAGEMODE_NV21 2
#define IMAGEMODE_RGBA 3

#define LANDMARK_ST_NR 106
#define ALPHA 0.7
#define BETA 0.3

struct ApiHandle {
	MG_FPP_APIHANDLE api;
	MG_FPP_IMAGEHANDLE imghandle;
	int points;
	int w, h, orientation;
};

/*
 * Class: com.megvii.fppapidemo.Api.nativeInit(Context, byte[])
 */
jlong Java_com_megvii_facepp_sdk_jni_FaceApi_nativeInit(JNIEnv *env, jobject,
		jobject context, jbyteArray model) {

	jbyte* model_data = env->GetByteArrayElements(model, 0);
	long model_len = env->GetArrayLength(model);

	ApiHandle *h = new ApiHandle();
	int retcode = mg_facepp.CreateApiHandle(env, context,
			reinterpret_cast<const MG_BYTE*>(model_data), model_len, &h->api);
	env->ReleaseByteArrayElements(model, model_data, 0);
	LOGE("nativeInit retcode: %d", retcode);
	if (retcode != 0) {
		return retcode;
	}

	h->imghandle = nullptr;

	return reinterpret_cast<jlong>(h);
}

jintArray Java_com_megvii_facepp_sdk_jni_FaceApi_nativeGetFaceppConfig(
		JNIEnv *env, jobject, jlong handle) {
	ApiHandle *h = reinterpret_cast<ApiHandle*>(handle);

	jintArray retArray = env->NewIntArray(8);

	MG_FPP_APICONFIG config;
	mg_facepp.GetDetectConfig(h->api, &config);

	int min_face_size = config.min_face_size;
	int rotation = config.rotation;
	int interval = config.interval;
	int detection_mode = config.detection_mode;

	env->SetIntArrayRegion(retArray, 0, 1, &min_face_size);
	env->SetIntArrayRegion(retArray, 1, 1, &rotation);
	env->SetIntArrayRegion(retArray, 2, 1, &interval);
	env->SetIntArrayRegion(retArray, 3, 1, &detection_mode);

	env->SetIntArrayRegion(retArray, 4, 1, &(config.roi.left));
	env->SetIntArrayRegion(retArray, 5, 1, &(config.roi.top));
	env->SetIntArrayRegion(retArray, 6, 1, &(config.roi.right));
	env->SetIntArrayRegion(retArray, 7, 1, &(config.roi.bottom));

	return retArray;
}

jint Java_com_megvii_facepp_sdk_jni_FaceApi_nativeSetFaceppConfig(JNIEnv *,
		jobject, jlong handle, jint minFaceSize, jint rotation, jint interval,
		jint detection_mode, jint left, jint top, jint right, jint bottom) {
	ApiHandle *h = reinterpret_cast<ApiHandle*>(handle);
	h->orientation = rotation;
	MG_FPP_APICONFIG config;
	mg_facepp.GetDetectConfig(h->api, &config);
	config.min_face_size = minFaceSize;
	config.rotation = rotation;
	config.interval = interval;
	if (detection_mode == DETECTION_TRACKING) {
		config.detection_mode = MG_FPP_DETECTIONMODE_TRACKING;
	} else
		config.detection_mode = MG_FPP_DETECTIONMODE_NORMAL;
	MG_RECTANGLE _roi;
	_roi.left = left;
	_roi.top = top;
	_roi.right = right;
	_roi.bottom = bottom;
	config.roi = _roi;
	int retcode = mg_facepp.SetDetectConfig(h->api, &config);
	return retcode;
}

/*
 * Class: com.megvii.fppapidemo.Api.nativeGetFacesPoint(jlong handle, img_data, img_width, img_hegiht)
 * Return: [[left, top, right, bottom], [(x,y) x 81]] * face_nr
 */
jint Java_com_megvii_facepp_sdk_jni_FaceApi_nativeDetect(JNIEnv *env, jobject,
		jlong handle, jbyteArray img, jint width, jint height, jint imageMode) {

	ApiHandle *h = reinterpret_cast<ApiHandle*>(handle);
	jbyte* img_data = env->GetByteArrayElements(img, 0);
	//LOGE("nativeDetect length: %d", 0);

	if (h->imghandle != nullptr && (h->w != width || h->h != height)) {
		mg_facepp.ReleaseImageHandle(h->imghandle);
		h->imghandle = nullptr;
	}
	if (h->imghandle == nullptr) {
		mg_facepp.CreateImageHandle(width, height, &h->imghandle);
		h->w = width;
		h->h = height;
	}

	MG_FPP_IMAGEHANDLE imageHandle = h->imghandle;
	//LOGE("nativeDetect length: %d, imageMode: %d,", 1, imageMode);
	if (imageMode == IMAGEMODE_GRAY) {
		mg_facepp.SetImageData(imageHandle, (unsigned char*) img_data,
				MG_IMAGEMODE_GRAY);
	} else if (imageMode == IMAGEMODE_BGR) {
		mg_facepp.SetImageData(imageHandle, (unsigned char*) img_data,
				MG_IMAGEMODE_BGR);
	} else if (imageMode == IMAGEMODE_NV21) {
		mg_facepp.SetImageData(imageHandle, (unsigned char*) img_data,
				MG_IMAGEMODE_NV21);
	} else if (imageMode == IMAGEMODE_RGBA) {
		mg_facepp.SetImageData(imageHandle, (unsigned char*) img_data,
				MG_IMAGEMODE_RGBA);
	}
	//LOGE("nativeDetect length: %d", 2);
	int faceCount = 0;
	mg_facepp.Detect(h->api, imageHandle, &faceCount);
	env->ReleaseByteArrayElements(img, img_data, 0); //release javabytearray
	return faceCount;
}

jfloatArray Java_com_megvii_facepp_sdk_jni_FaceApi_nativeFaceInfo(JNIEnv *env,
		jobject, jlong handle, jint index) {
	ApiHandle *h = reinterpret_cast<ApiHandle*>(handle);
	jfloatArray retArray = env->NewFloatArray(MG_LANDMARK_NR * 2 + 10);
	MG_FACE face;
	mg_facepp.GetFaceInfo(h->api, index, &face);

	float track_id = face.track_id;
	env->SetFloatArrayRegion(retArray, 0, 1, &(track_id));
	float idx = index;
	env->SetFloatArrayRegion(retArray, 1, 1, &(idx));

	env->SetFloatArrayRegion(retArray, 2, 1, &(face.confidence));
	MG_RECTANGLE rect = face.rect;
	float left = rect.left;
	float top = rect.top;
	float right = rect.right;
	float bottom = rect.bottom;
	env->SetFloatArrayRegion(retArray, 3, 1, &left);
	env->SetFloatArrayRegion(retArray, 4, 1, &top);
	env->SetFloatArrayRegion(retArray, 5, 1, &right);
	env->SetFloatArrayRegion(retArray, 6, 1, &bottom);

	MG_FACELANDMARKS facelandmark = face.points;
	MG_POINT *points = facelandmark.point;
	for (int j = 0; j < MG_LANDMARK_NR; ++j) {
		float point[2];
		point[0] = points[j].x;
		point[1] = points[j].y;
		rotate_point_2d(h->w, h->h, point[0], point[1], h->orientation);

		env->SetFloatArrayRegion(retArray, 7 + j * 2, 2, point);
	}

	return retArray;
}

jfloatArray Java_com_megvii_facepp_sdk_jni_FaceApi_nativeLandMark(JNIEnv *env,
		jobject, jlong handle, jint index, jint point_nr) {
	ApiHandle *h = reinterpret_cast<ApiHandle*>(handle);
	jfloatArray retArray = env->NewFloatArray(LANDMARK_ST_NR * 2);
	MG_POINT buff[LANDMARK_ST_NR];
	mg_facepp.GetLandmark(h->api, index, true, point_nr, buff);
	for (int j = 0; j < point_nr; ++j) {
		float point[2];
		point[0] = buff[j].x;
		point[1] = buff[j].y;
		MG_FPP_ATTR_AGE_GENDER;
		rotate_point_2d(h->w, h->h, point[0], point[1], h->orientation);

		env->SetFloatArrayRegion(retArray, j * 2, 2, point);
	}

	return retArray;
}

jfloatArray Java_com_megvii_facepp_sdk_jni_FaceApi_nativeAttribute(JNIEnv *env,
		jobject, jlong handle, jint index) {
	ApiHandle *h = reinterpret_cast<ApiHandle*>(handle);
	jfloatArray retArray = env->NewFloatArray(LANDMARK_ST_NR * 2);
	MG_FACE mgFace;
	mg_facepp.GetAttribute(h->api, h->imghandle, index,
			MG_FPP_ATTR_POSE3D | MG_FPP_ATTR_EYESTATUS | MG_FPP_ATTR_MOUTHSTATUS
					| MG_FPP_ATTR_MINORITY | MG_FPP_ATTR_BLURNESS
					| MG_FPP_ATTR_AGE_GENDER, &mgFace);
	float pitch = mgFace.pose.pitch;
	float yaw = mgFace.pose.yaw;
	float roll = mgFace.pose.roll;
	env->SetFloatArrayRegion(retArray, 0, 1, &pitch);
	env->SetFloatArrayRegion(retArray, 1, 1, &yaw);
	env->SetFloatArrayRegion(retArray, 2, 1, &roll);

	int offset = 3;

	for (int i = 0; i < MG_EYESTATUS_COUNT; ++i) {
		float left_eyestatu = mgFace.left_eyestatus[i];
		env->SetFloatArrayRegion(retArray, i + offset, 1, &left_eyestatu);
	}
	offset += MG_EYESTATUS_COUNT;
	for (int i = 0; i < MG_EYESTATUS_COUNT; ++i) {
		float right_eyestatu = mgFace.right_eyestatus[i];
		env->SetFloatArrayRegion(retArray, i + offset, 1, &right_eyestatu);
	}
	offset += MG_EYESTATUS_COUNT;

	for (int i = 0; i < MG_MOUTHSTATUS_COUNT; ++i) {
		float moutstatu = mgFace.moutstatus[i];
		env->SetFloatArrayRegion(retArray, i + offset, 1, &moutstatu);
	}
	offset += MG_MOUTHSTATUS_COUNT;

	float minority = mgFace.minority;
	env->SetFloatArrayRegion(retArray, offset, 1, &minority);
	float blurness = mgFace.blurness;
	env->SetFloatArrayRegion(retArray, offset + 1, 1, &blurness);
	float age = mgFace.age;
	env->SetFloatArrayRegion(retArray, offset + 2, 1, &age);
	MG_GENDER gender = mgFace.gender;
	float female = gender.female;
	float male = gender.male;
	env->SetFloatArrayRegion(retArray, offset + 3, 1, &female);
	env->SetFloatArrayRegion(retArray, offset + 4, 1, &male);

	return retArray;
}
jfloatArray Java_com_megvii_facepp_sdk_jni_FaceApi_nativePose3D(JNIEnv *env,
		jobject, jlong handle, jint index) {
	ApiHandle *h = reinterpret_cast<ApiHandle*>(handle);
	jfloatArray retArray = env->NewFloatArray(LANDMARK_ST_NR * 2);
	MG_FACE mgFace;
	mg_facepp.GetAttribute(h->api, h->imghandle, index, MG_FPP_ATTR_POSE3D,
			&mgFace);
	float pitch = mgFace.pose.pitch;
	float yaw = mgFace.pose.yaw;
	float roll = mgFace.pose.roll;
	env->SetFloatArrayRegion(retArray, 0, 1, &pitch);
	env->SetFloatArrayRegion(retArray, 1, 1, &yaw);
	env->SetFloatArrayRegion(retArray, 2, 1, &roll);

	return retArray;
}

jfloatArray Java_com_megvii_facepp_sdk_jni_FaceApi_nativeEyeStatus(JNIEnv *env,
		jobject, jlong handle, jint index) {
	ApiHandle *h = reinterpret_cast<ApiHandle*>(handle);
	jfloatArray retArray = env->NewFloatArray(LANDMARK_ST_NR * 2);
	MG_FACE mgFace;
	mg_facepp.GetAttribute(h->api, h->imghandle, index, MG_FPP_ATTR_EYESTATUS,
			&mgFace);
	for (int i = 0; i < MG_EYESTATUS_COUNT; ++i) {
		float left_eyestatu = mgFace.left_eyestatus[i];
		env->SetFloatArrayRegion(retArray, i, 1, &left_eyestatu);
	}
	for (int i = 0; i < MG_EYESTATUS_COUNT; ++i) {
		float right_eyestatu = mgFace.right_eyestatus[i];
		env->SetFloatArrayRegion(retArray, MG_EYESTATUS_COUNT + i, 1,
				&right_eyestatu);
	}
	return retArray;
}

jfloatArray Java_com_megvii_facepp_sdk_jni_FaceApi_nativeMouthStatus(
		JNIEnv *env, jobject, jlong handle, jint index) {
	ApiHandle *h = reinterpret_cast<ApiHandle*>(handle);
	jfloatArray retArray = env->NewFloatArray(LANDMARK_ST_NR * 2);
	MG_FACE mgFace;
	mg_facepp.GetAttribute(h->api, h->imghandle, index, MG_FPP_ATTR_MOUTHSTATUS,
			&mgFace);
	for (int i = 0; i < MG_MOUTHSTATUS_COUNT; ++i) {
		float moutstatu = mgFace.moutstatus[i];
		env->SetFloatArrayRegion(retArray, i, 1, &moutstatu);
	}

	return retArray;
}

jfloatArray Java_com_megvii_facepp_sdk_jni_FaceApi_nativeMinority(JNIEnv *env,
		jobject, jlong handle, jint index) {
	ApiHandle *h = reinterpret_cast<ApiHandle*>(handle);
	jfloatArray retArray = env->NewFloatArray(LANDMARK_ST_NR * 2);
	MG_FACE mgFace;
	mg_facepp.GetAttribute(h->api, h->imghandle, index, MG_FPP_ATTR_MINORITY,
			&mgFace);
	float minority = mgFace.minority;
	env->SetFloatArrayRegion(retArray, 0, 1, &minority);

	return retArray;
}

jfloatArray Java_com_megvii_facepp_sdk_jni_FaceApi_nativeBlurness(JNIEnv *env,
		jobject, jlong handle, jint index) {
	ApiHandle *h = reinterpret_cast<ApiHandle*>(handle);
	jfloatArray retArray = env->NewFloatArray(LANDMARK_ST_NR * 2);
	MG_FACE mgFace;
	mg_facepp.GetAttribute(h->api, h->imghandle, index, MG_FPP_ATTR_BLURNESS,
			&mgFace);
	float blurness = mgFace.blurness;
	env->SetFloatArrayRegion(retArray, 0, 1, &blurness);

	return retArray;
}

jfloatArray Java_com_megvii_facepp_sdk_jni_FaceApi_nativeAgeGender(JNIEnv *env,
		jobject, jlong handle, jint index) {
	ApiHandle *h = reinterpret_cast<ApiHandle*>(handle);
	jfloatArray retArray = env->NewFloatArray(LANDMARK_ST_NR * 2);
	MG_FACE mgFace;
	mg_facepp.GetAttribute(h->api, h->imghandle, index, MG_FPP_ATTR_AGE_GENDER,
			&mgFace);
	float age = mgFace.age;
	MG_GENDER gender = mgFace.gender;
	float female = gender.female;
	float male = gender.male;
	env->SetFloatArrayRegion(retArray, 0, 1, &age);
	env->SetFloatArrayRegion(retArray, 1, 1, &female);
	env->SetFloatArrayRegion(retArray, 2, 1, &male);

	return retArray;
}

/*
 * Class: com.megvii.fppapidemo.Api.NativeRelease(jlong handle)
 */
void Java_com_megvii_facepp_sdk_jni_FaceApi_nativeRelease(JNIEnv*, jobject,
		jlong handle) {

	ApiHandle *h = reinterpret_cast<ApiHandle*>(handle);
	if (h->imghandle != nullptr)
		mg_facepp.ReleaseImageHandle(h->imghandle);

	mg_facepp.ReleaseApiHandle(h->api);
	delete h;
}

jlong Java_com_megvii_facepp_sdk_jni_FaceApi_nativeGetApiExpication(JNIEnv *env,
		jobject, jobject ctx) {

	return (long) mg_facepp.GetApiExpiration(env, ctx);
}

jstring Java_com_megvii_facepp_sdk_jni_FaceApi_nativeGetVersion(JNIEnv *env,
		jobject) {
	const char* version = mg_facepp.GetApiVersion();
	return env->NewStringUTF(version);
}

jlong Java_com_megvii_facepp_sdk_jni_FaceApi_nativeGetApiName(JNIEnv *,
		jobject) {
	return (jlong)(mg_facepp.GetApiVersion);
}
