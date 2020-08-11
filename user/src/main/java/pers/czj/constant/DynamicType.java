package pers.czj.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.Getter;

/**
 * 创建在 2020/8/9 22:30
 * 动态类型常量
 */
@Getter
public enum DynamicType implements IEnum<Integer> {

    // 视频   帖子      个人
    VIDEO(0),POST(1),PERSONAL(2);

    private int type;

    DynamicType(int type) {
        this.type = type;
    }

    @Override
    public Integer getValue() {
        return type;
    }
}
