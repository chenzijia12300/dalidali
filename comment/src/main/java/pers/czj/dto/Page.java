package pers.czj.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 创建在 2020/7/15 21:25
 */
@ApiModel
@Data
public class Page {

    public Page() {
        pageNum = 1;
        pageSize = 8;
    }

    @Min(1)
    @ApiModelProperty("第几页")
    private int pageNum;

    @Max(20)
    @ApiModelProperty("大小")
    private int pageSize;
}
