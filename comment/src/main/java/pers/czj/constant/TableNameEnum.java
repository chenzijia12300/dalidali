package pers.czj.constant;

import lombok.Getter;

/**
 * 创建在 2020/7/15 17:42
 * 表名枚举
 */
@Getter
public enum TableNameEnum {
    VIDEO("video"),POST("post"),DYNAMIC("dynamic");

    String name;

    TableNameEnum(String name) {
        this.name = name;
    }
}
