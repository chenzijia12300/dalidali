package pers.czj.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import pers.czj.constant.VideoResolutionEnum;
import pers.czj.entity.Video;

/**
 * 创建在 2020/7/11 23:44
 */
@ApiModel("视频传输输入类")
@Data
public class VideoInputDto {

    @ApiModelProperty("视频的顶级（父）频道")
    private long categoryPId;

    @ApiModelProperty("视频所属频道")
    private long categoryId;

    @ApiModelProperty("视频标题")
    private String title;

    @ApiModelProperty("视频基本地址，根据清晰度确定准确地址")
    private String basicUrl;

    @ApiModelProperty("视频分辨率枚举类")
    private VideoResolutionEnum resolutionEnum;

    public Video convert(){
        Video video = new Video();
        BeanUtils.copyProperties(this,video);
        return video;
    }
}
