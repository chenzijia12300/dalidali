package pers.czj.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import pers.czj.constant.TableNameEnum;

/**
 * 创建在 2020/7/15 21:31
 */
@ApiModel
@Data
public class CommonRequest {

    @ApiModelProperty("查询的表名")
    private TableNameEnum name;

    @ApiModelProperty("对应主键")
    private long id;
}
