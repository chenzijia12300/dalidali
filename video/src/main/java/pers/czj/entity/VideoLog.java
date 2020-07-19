package pers.czj.entity;

import lombok.Data;

/**
 * 创建在 2020/7/19 21:44
 */
@Data
public class VideoLog {

    private long id;

    private long uid;

    private long vid;

    private Boolean isPraise;

    private Boolean isCollection;

    private Integer coinNum;
}
