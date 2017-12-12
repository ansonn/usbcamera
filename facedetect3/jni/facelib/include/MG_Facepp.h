#ifndef _MG_FACEPP_H_
#define _MG_FACEPP_H_

#include "MG_Common.h"

#define _OUT

#ifdef __cplusplus
extern "C"{
#endif

#define MG_FPP_GET_LANDMARK106 106
#define MG_FPP_GET_LANDMARK81 81

#define MG_FPP_ATTR_POSE3D 0x01
#define MG_FPP_ATTR_EYESTATUS 0x02
#define MG_FPP_ATTR_MOUTHSTATUS 0x04

typedef enum {
	// Detect a single image
    MG_FPP_DETECTIONMODE_NORMAL = 0,

    // Detect a video with series frame
    MG_FPP_DETECTIONMODE_TRACKING,
}	MG_FPP_DETECTIONMODE;

// API Handle
struct _MG_FPP_API;
typedef struct _MG_FPP_API* MG_FPP_APIHANDLE;

// Image Handle
struct _MG_FPP_IMAGE;
typedef struct _MG_FPP_IMAGE* MG_FPP_IMAGEHANDLE;

// API Config
typedef struct {
	// min face size for face detection
	MG_UINT32 min_face_size;

	// orientation of input image, 360 degree base, clockwise
	// invalid values: 0, 90, 180, 270, 360
	MG_UINT32 rotation;

	// frame's interval for face detection
	// (per [interval] frames detection full image once)
	MG_UINT32 interval;

	// tracking or one single image detection
	MG_FPP_DETECTIONMODE detection_mode;
    MG_RECTANGLE roi;
} MG_FPP_APICONFIG;


typedef struct {
#if MGAPI_BUILD_ON_ANDROID
 	// Create an api handle
 	// Create with model data and check the package name
	MG_RETCODE (*CreateApiHandle)
		(JNIEnv* , jobject, const MG_BYTE *model_data, MG_INT32 model_length, MG_FPP_APIHANDLE _OUT *api_handle_ptr);
#else
	// Create an api handle
	// Create with model data and check the bundle-id
	MG_RETCODE (*CreateApiHandle)
		(const MG_BYTE *model_data, MG_INT32 model_length, MG_FPP_APIHANDLE _OUT *api_handle_ptr);

#endif

 	// Release api handle
 	MG_RETCODE (*ReleaseApiHandle)
 		(MG_FPP_APIHANDLE);

    const char* (*GetApiVersion)();

#if MGAPI_BUILD_ON_ANDROID
    MG_UINT64 (*GetApiExpiration)(JNIEnv*, jobject);
#else
    MG_UINT64 (*GetApiExpiration)();
#endif

 	// Get config from api handle
 	MG_RETCODE (*GetDetectConfig)
 		(MG_FPP_APIHANDLE api_handle, MG_FPP_APICONFIG _OUT *config);

 	// Set config to api handle
 	MG_RETCODE (*SetDetectConfig)
 		(MG_FPP_APIHANDLE api_handle, const MG_FPP_APICONFIG *config);

 	// Detect faces
 	MG_RETCODE (*Detect)
 		(MG_FPP_APIHANDLE api_handle, MG_FPP_IMAGEHANDLE image_handle, MG_INT32 _OUT *face_nr);

 	// Get face info from last detected frame
 	MG_RETCODE (*GetFaceInfo)
 		(MG_FPP_APIHANDLE api_handle, MG_INT32 idx, MG_FACE _OUT *face);

 	// Get landmarks
 	// you can choose smooth or not, 81p or 106p
 	MG_RETCODE (*GetLandmark)
 		(MG_FPP_APIHANDLE api_handle, MG_INT32 idx, MG_BOOL is_smooth, MG_INT32 nr, MG_POINT _OUT *points);
    MG_RETCODE (*GetAttribute)
    (MG_FPP_APIHANDLE api_Handle, MG_FPP_IMAGEHANDLE image_handle, MG_INT32 idx, MG_INT32 attribute_mode,MG_FACE _OUT * face);

	// Create an image handle
	// can't modify image size anymore
	MG_RETCODE (*CreateImageHandle)
		(MG_INT32 width, MG_INT32 height, MG_FPP_IMAGEHANDLE _OUT *image_handle_ptr);

	// Set raw data into image handle
	// you can set raw data many times
	MG_RETCODE (*SetImageData)
		(MG_FPP_IMAGEHANDLE image_handle, const MG_BYTE *image_data, MG_IMAGEMODE image_mode);

	// Release image hanlde
 	MG_RETCODE (*ReleaseImageHandle)
 		(MG_FPP_IMAGEHANDLE image_Handle);
}	MG_FACEPP_API_FUNCTIONS_TYPE;

// Exported api_handle for using algorithm
// Example:
// 		mg_facepp.CreateApiHandle(...
// 		mg_facepp.Detect(...
extern MG_EXPORT MG_FACEPP_API_FUNCTIONS_TYPE mg_facepp;

#ifdef __cplusplus
}
#endif

#undef _OUT
#endif // _MG_FACEPP_H_
