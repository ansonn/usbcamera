#include <jni.h>

#ifndef _Included_com_megvii_fppapidemo_Api
#define _Included_com_megvii_fppapidemo_Api
#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jlong JNICALL Java_com_megvii_facepp_sdk_jni_FaceApi_nativeInit(JNIEnv *, jobject,
		jobject, jbyteArray);

JNIEXPORT jintArray JNICALL Java_com_megvii_facepp_sdk_jni_FaceApi_nativeGetFaceppConfig(
		JNIEnv *env, jobject, jlong);

JNIEXPORT jint JNICALL Java_com_megvii_facepp_sdk_jni_FaceApi_nativeSetFaceppConfig(JNIEnv *, jobject, jlong, jint, jint, jint,
		jint, jint, jint, jint, jint);

JNIEXPORT jint JNICALL Java_com_megvii_facepp_sdk_jni_FaceApi_nativeDetect(JNIEnv *, jobject,
		jlong, jbyteArray, jint, jint, jint);

JNIEXPORT jfloatArray JNICALL Java_com_megvii_facepp_sdk_jni_FaceApi_nativeFaceInfo(JNIEnv *,
		jobject, jlong, jint);

JNIEXPORT jfloatArray JNICALL Java_com_megvii_facepp_sdk_jni_FaceApi_nativeLandMark(JNIEnv *,
		jobject, jlong, jint, jint);

JNIEXPORT jfloatArray JNICALL Java_com_megvii_facepp_sdk_jni_FaceApi_nativeAttribute(JNIEnv *,
		jobject, jlong, jint);

JNIEXPORT void JNICALL Java_com_megvii_facepp_sdk_jni_FaceApi_nativeRelease(JNIEnv*, jobject,
		jlong);

JNIEXPORT jlong JNICALL Java_com_megvii_facepp_sdk_jni_FaceApi_nativeGetApiExpication(JNIEnv *,
		jobject, jobject);

JNIEXPORT jstring JNICALL Java_com_megvii_facepp_sdk_jni_FaceApi_nativeGetVersion(JNIEnv *,
		jobject);

JNIEXPORT jlong JNICALL Java_com_megvii_facepp_sdk_jni_FaceApi_nativeGetApiName(JNIEnv *,
		jobject);

#ifdef __cplusplus
}
#endif
#endif
