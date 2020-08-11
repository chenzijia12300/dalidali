package pers.czj.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.Getter;

/**
 * 创建在 2020/8/9 22:36
 */
@Getter
public enum ActionType implements IEnum<Integer> {
    FOLLOW(0),PRAISE(1),COMMENT(2),REPLY(3);


    private int type;

    ActionType(int type) {
        this.type = type;
    }

    @Override
    public Integer getValue() {
        return type;
    }
}
