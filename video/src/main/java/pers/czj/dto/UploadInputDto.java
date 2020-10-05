package pers.czj.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 创建在 2020/9/5 21:21
 */
@Data
@ApiModel
public class UploadInputDto {

    private String title;

    private String videoUrl;

    private long cid;

    private long cPid;


}
