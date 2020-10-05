package pers.czj.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 创建在 2020/9/22 20:42
 */
@ApiModel("android Apk相关信息")
@Data
public class ApkInfo {


    private long id;

    @ApiModelProperty("apk版本号")
    private String version;

    @ApiModelProperty("更新描述")
    private String description;

    @ApiModelProperty("Apk下载路径")
    private String url;

    @ApiModelProperty("更新时间(默认为当天)")
    private Date createTime;

}
