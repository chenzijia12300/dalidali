package pers.czj.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 创建在 2020/10/29 19:30
 */
public enum VideoScreenTypeEnum implements IEnum<Integer> {

    LANDSCAPE(0),PORTRAIT(1);


    int code;

    VideoScreenTypeEnum(int code) {
        this.code = code;
    }

    @Override
    public Integer getValue() {
        return code;
    }
}
