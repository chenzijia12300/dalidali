package pers.czj.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 创建在 2020/7/30 18:38
 */
@Getter
public enum DanmuLocationEnum implements IEnum<Integer> {
    TOP(1), BOTTOM(2), STANDARD(0);
    int location;

    DanmuLocationEnum(int location) {
        this.location = location;
    }

    @Override
    public Integer getValue() {
        return location;
    }


    @Override
    @JsonValue
    public String toString() {
        return name().toLowerCase();
    }

}
