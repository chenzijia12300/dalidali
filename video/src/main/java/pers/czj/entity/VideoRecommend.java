package pers.czj.entity;

import lombok.Data;
import pers.czj.constant.RecommendLocation;

import java.util.Date;

/**
 * 创建在 2020/7/26 13:47
 */
@Data
public class VideoRecommend {

    private long id;

    private long vid;

    private boolean status;

    private int sort;

    private Date createTime;

    private Date endTime;

    private RecommendLocation location;
}
