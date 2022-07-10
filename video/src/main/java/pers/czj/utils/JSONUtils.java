package pers.czj.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 创建在 2020/12/2 20:26
 */
public class JSONUtils {

    public static JSONObject get(String str, String key) {
        JSONObject jsonObject = JSON.parseObject(str);
        return jsonObject.getJSONObject(key);
    }

    public static JSONObject get(JSONObject jsonObject, String key) {
        return jsonObject.getJSONObject(key);
    }

    public static JSONArray getArr(JSONObject jsonObject, String key) {
        return jsonObject.getJSONArray(key);
    }
}
