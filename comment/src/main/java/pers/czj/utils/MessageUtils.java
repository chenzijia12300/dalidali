package pers.czj.utils;

import pers.czj.constant.ActionType;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建在 2020/8/10 22:35
 */
public class MessageUtils {

    public static Map<String, Object> createMessage(long sendUserId, long receiveUserId, ActionType type, String content) {
        Map<String, Object> map = new HashMap<>();
        map.put("sendUid", sendUserId);
        map.put("receiveUid", receiveUserId);
        map.put("type", type);
        map.put("content", content);
        return map;
    }
}
