package pers.czj.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;

/**
 * 创建在 2020/7/19 21:44
 */
@Data
@Accessors(chain = true)
public class VideoLog {

    private long id;

    private long uid;

    private long vid;

    private Boolean isPraise;

    private Boolean isCollection;

    @Max(2)
    private Integer coinNum;
}
