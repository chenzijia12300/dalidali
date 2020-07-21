package pers.czj.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 创建在 2020/7/20 23:31
 */
@Data
@ApiModel
public class CoinInputDto {

    private long vid;

    @Max(value = 2,message = "最多投两枚硬币喔")
    @Min(value = 1,message = "至少投一枚硬币喔")
    private int num;
}
