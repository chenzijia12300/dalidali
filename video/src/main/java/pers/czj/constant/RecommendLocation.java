package pers.czj.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 创建在 2020/8/14 22:07
 */
public enum RecommendLocation implements IEnum<Integer> {
    LEFT(0),RIGHT(1);

    int code;

    RecommendLocation(int code) {
        this.code = code;
    }

    @Override
    public Integer getValue() {
        return code;
    }
}
