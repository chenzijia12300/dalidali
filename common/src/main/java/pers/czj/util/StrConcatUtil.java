package pers.czj.util;

import net.jcip.annotations.NotThreadSafe;

/**
 * 创建在 2020/12/2 9:00
 */
@NotThreadSafe
public class StrConcatUtil {

    private String connector;

    public StrConcatUtil(String connector) {
        this.connector = connector;
    }

    /**
     * 连接字符串，每次连接添加connector字符
     *
     * @param [strs]
     * @return java.lang.String
     * @author czj
     * @date 2020/12/2 9:03
     */
    public String concat(Object... strs) {

        if (strs == null || strs.length == 0) {
            return null;
        }

        if (strs.length == 1) {
            return strs[0].toString();
        }

        StringBuilder builder = new StringBuilder();
        for (Object str : strs) {
            builder.append(str);
            builder.append(connector);
        }
        return builder.substring(0, builder.length() - 1);
    }

}
