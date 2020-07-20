package pers.czj.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 创建在 2020/7/20 23:31
 */
@Data
@ApiModel
public class CoinInputDto {

    private long vid;

    private int num;
}
