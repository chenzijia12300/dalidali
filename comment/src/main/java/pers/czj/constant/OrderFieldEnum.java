package pers.czj.constant;

import lombok.Getter;

/**
 * 创建在 2020/7/30 21:11
 */
@Getter
public enum OrderFieldEnum {

    PRAISE("praise_num"), CREATE_TIME("create_time");
    String field;

    OrderFieldEnum(String field) {
        this.field = field;
    }
}
