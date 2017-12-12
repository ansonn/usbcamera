package com.sendong.facesystem.util;

import android.text.TextUtils;
import java.util.HashMap;

public class FaceOperate
{
  private String apiKey = "";
  private String apiSecret = "";
  
  public FaceOperate(String apiKey, String apiSecret)
  {
    this.apiKey = apiKey;
    this.apiSecret = apiSecret;
  }
  
  public byte[] analyzeFace(String faceTokens, int returnLandmark, String returnAttributes, String returnFaceQuality)
    throws Exception
  {
    String url = "https://api.megvii.com/facepp/v3/face/analyze";
    HashMap<String, String> map = new HashMap();
    map.put("api_key", this.apiKey);
    map.put("api_secret", this.apiSecret);
    map.put("face_tokens", faceTokens);
    map.put("return_landmark", String.valueOf(returnLandmark));
    if (!TextUtils.isEmpty(returnAttributes)) {
      map.put("return_attributes", returnAttributes);
    }
    if (!TextUtils.isEmpty(returnFaceQuality)) {
      map.put("return_face_quality", returnFaceQuality);
    }
    return HttpRequest.post(url, map);
  }
  
  public byte[] faceGetDetail(String faceToken)
    throws Exception
  {
    String url = "https://api.megvii.com/facepp/v3/face/analyze";
    HashMap<String, String> map = new HashMap();
    map.put("api_key", this.apiKey);
    map.put("api_secret", this.apiSecret);
    map.put("face_token", faceToken);
    return HttpRequest.post(url, map);
  }
  
  public byte[] faceSetUserId(String faceToken, String userId)
    throws Exception
  {
    String url = "https://api.megvii.com/facepp/v3/face/setuserid";
    HashMap<String, String> map = new HashMap();
    map.put("api_key", this.apiKey);
    map.put("api_secret", this.apiSecret);
    map.put("face_token", faceToken);
    map.put("user_id", userId);
    return HttpRequest.post(url, map);
  }
}
