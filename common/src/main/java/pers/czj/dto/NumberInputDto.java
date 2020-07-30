package pers.czj.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 创建在 2020/7/30 17:30
 */
@Data
@ApiModel("属性数值输入传输类")
public class NumberInputDto {

    private long id;

    private int num;

    public NumberInputDto(long id, int num) {
        this.id = id;
        this.num = num;
    }

    public NumberInputDto() {
    }
}
