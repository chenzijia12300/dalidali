package pers.czj.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * 创建在 2020/7/11 14:37
 */
@ApiModel("视频分类传输输出类")
@Data
public class CategoryOutputDto {

    private long id;

    private long pid;

    private String name;

    private long proNum;

    private long dayProNum;

    private List<CategoryOutputDto> childList;
}
