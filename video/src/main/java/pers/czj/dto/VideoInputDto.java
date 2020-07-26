package pers.czj.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import pers.czj.constant.VideoPublishStateEnum;
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
    private String urls;

    @ApiModelProperty("标签们")
    private String tags;

    @ApiModelProperty("视频简介")
    private String description;

    private long uid;

/*    @ApiModelProperty("视频的顶级频道（冗余）")
    private String categoryPName;

    @ApiModelProperty("视频所属频道（冗余）")
    private String categoryName;*/


    public Video convert(){
        Video video = new Video();
        BeanUtils.copyProperties(this,video);
        video.setPublishState(VideoPublishStateEnum.AUDIT);
        video.setResolutionState(VideoResolutionEnum.P_1080);
        return video;
    }
}
