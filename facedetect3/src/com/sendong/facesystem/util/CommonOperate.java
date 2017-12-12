package com.sendong.facesystem.util;

import java.io.File;
import java.util.HashMap;

/**
 * 杩欎釜绫婚噷鐨勬墍鏈夋柟娉曢兘鏄綉缁滆姹傦紝鎵�浠ヨ鍦ㄥ紓姝ョ嚎绋嬩腑璋冪敤
 */
public class CommonOperate {

    private String apiKey = "";
    private String apiSecret = "";

    public CommonOperate(String apiKey, String apiSecret){
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    /**
     * 璋冪敤鑰呮彁渚涘浘鐗囨枃浠舵垨鑰呭浘鐗嘦RL锛岃繘琛屼汉鑴告娴嬨�傝瘑鍒嚭鐨勪汉鑴镐細缁欏嚭浜鸿劯鏍囪瘑face_token锛岀敤浜庡悗缁殑浜鸿劯姣斿銆佹娴嬩汉鑴稿睘鎬у拰鍏抽敭鐐圭瓑鎿嶄綔銆�
     * @param imageUrl 鍥剧墖閾炬帴
     * @return 杩斿洖缁勬灉鐨刡yte鏁扮粍
     * @throws Exception
     */
    public byte[] detectUrl(String imageUrl) throws Exception {
        String url = Key.WEB_BASE + Key.SPLIT + Key.DETECT;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        map.put(Key.KEY_FOR_IMAGE_URL, imageUrl);
        return HttpRequest.post(url, map);
    }

    /**
     *  璋冪敤鑰呮彁渚涘浘鐗囨枃浠舵垨鑰呭浘鐗嘦RL锛岃繘琛屼汉鑴告娴嬨�傝瘑鍒嚭鐨勪汉鑴镐細缁欏嚭浜鸿劯鏍囪瘑face_token锛岀敤浜庡悗缁殑浜鸿劯姣斿銆佹娴嬩汉鑴稿睘鎬у拰鍏抽敭鐐圭瓑鎿嶄綔銆�
     * @param file 鏂囦欢
     * @return 杩斿洖缁勬灉鐨刡yte鏁扮粍
     * @throws Exception
     */
    public byte[] detectFile(File file) throws Exception {
        String url = Key.WEB_BASE + Key.SPLIT + Key.DETECT;
        HashMap<String, String> map = new HashMap<String,String>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        return HttpRequest.post(url, map, null, file);
    }

    /**
     *  璋冪敤鑰呮彁渚涘浘鐗囨枃浠舵垨鑰呭浘鐗嘦RL锛岃繘琛屼汉鑴告娴嬨�傝瘑鍒嚭鐨勪汉鑴镐細缁欏嚭浜鸿劯鏍囪瘑face_token锛岀敤浜庡悗缁殑浜鸿劯姣斿銆佹娴嬩汉鑴稿睘鎬у拰鍏抽敭鐐圭瓑鎿嶄綔銆�
     * @param fileByte 浜岃繘鍒舵暟缁�
     * @return 杩斿洖缁勬灉鐨刡yte鏁扮粍
     * @throws Exception
     */
    public byte[] detectByte(byte[] fileByte) throws Exception {
        String url = Key.WEB_BASE + Key.SPLIT + Key.DETECT;
        HashMap<String, String> map = new HashMap<String,String>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        return HttpRequest.post(url, map, fileByte, null);
    }

    /**
     * 灏嗕袱涓汉鑴歌繘琛屾瘮瀵癸紝鏉ュ垽鏂槸鍚︿负鍚屼竴涓汉銆傞渶瑕佸厛浣跨敤Detect API妫�娴嬪浘鐗囦腑鐨勪汉鑴革紝骞惰褰曚笅浜鸿劯鏍囪瘑face_token鍚庝紶鍏ompare API杩涜浜鸿劯姣斿銆�
     * @param faceToken1 绗竴涓汉鑴告爣璇唂ace_token
     * @param faceToken2 绗簩涓汉鑴告爣璇唂ace_token
     * @return 杩斿洖缁勬灉鐨刡yte鏁扮粍
     * @throws Exception
     */
    public byte[] compare(String faceToken1, String faceToken2) throws Exception {
        String url = Key.WEB_BASE + Key.SPLIT + Key.COMPARE;
        HashMap<String, String> map = new HashMap<String,String>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        map.put(Key.KEY_FOR_FACE_TOKEN1, faceToken1);
        map.put(Key.KEY_FOR_FACE_TOKEN2, faceToken2);
        return HttpRequest.post(url, map);
    }

    /**
     *  鍦‵aceset涓壘鍑轰笌鐩爣浜鸿劯鏈�鐩镐技鐨勪竴寮犳垨澶氬紶浜鸿劯銆�
     * @param faceToken 涓嶧aceset涓汉鑴告瘮瀵圭殑face_token
     * @param faceSetToken Faceset鐨勬爣璇�
     * @param returnResultCount 杩斿洖姣斿缃俊搴︽渶楂樼殑n涓粨鏋滐紝鑼冨洿[1,5]銆傞粯璁ゅ�间负1
     * @return 杩斿洖缁勬灉鐨刡yte鏁扮粍
     * @throws Exception
     */
    public byte[] searchByFaceSetToken(String faceToken, String faceSetToken, int returnResultCount) throws Exception {
        String url = Key.WEB_BASE + Key.SPLIT + Key.SEARCH;
        HashMap<String, String> map = new HashMap<String,String>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        map.put(Key.KEY_FOR_FACE_TOKEN, faceToken);
        map.put(Key.KEY_FOR_FACESET_TOKEN, faceSetToken);
        map.put(Key.KEY_FOR_RETURN_RESULT_COUNT, String.valueOf(returnResultCount));
        return HttpRequest.post(url, map);
    }


    /**
     *  鍦‵aceset涓壘鍑轰笌鐩爣浜鸿劯鏈�鐩镐技鐨勪竴寮犳垨澶氬紶浜鸿劯銆�
     * @param faceToken 涓嶧aceset涓汉鑴告瘮瀵圭殑face_token
     * @param outerId 鐢ㄦ埛鑷畾涔夌殑Faceset鏍囪瘑
     * @param returnResultCount 杩斿洖姣斿缃俊搴︽渶楂樼殑n涓粨鏋滐紝鑼冨洿[1,5]銆傞粯璁ゅ�间负1
     * @return 杩斿洖缁勬灉鐨刡yte鏁扮粍
     * @throws Exception
     */
    public byte[] searchByOuterId(String faceToken, String outerId, int returnResultCount) throws Exception {
        String url = Key.WEB_BASE + Key.SPLIT + Key.SEARCH;
        HashMap<String, String> map = new HashMap<String,String>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        map.put(Key.KEY_FOR_FACE_TOKEN, faceToken);
        map.put(Key.KEY_FOR_OUTER_ID, outerId);
        map.put(Key.KEY_FOR_RETURN_RESULT_COUNT, String.valueOf(returnResultCount));
        return HttpRequest.post(url, map);
    }

}
