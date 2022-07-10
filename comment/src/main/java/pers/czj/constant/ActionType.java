package pers.czj.constant;

import lombok.Getter;

/**
 * 创建在 2020/8/9 22:36
 */
@Getter
public enum ActionType {
    FOLLOW(0), PRAISE(1), COMMENT(2), REPLY(3);


    private int type;

    ActionType(int type) {
        this.type = type;
    }
}
