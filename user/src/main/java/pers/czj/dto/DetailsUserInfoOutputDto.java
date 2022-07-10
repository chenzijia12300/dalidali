package pers.czj.dto;

import lombok.Data;

/**
 * 创建在 2020/11/6 23:24
 */
@Data
public class DetailsUserInfoOutputDto {

    /**
     * 自增唯一主键
     */
    private long id;


    /**
     * 用户名字
     */
    private String username;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户头像
     */
    private String img;

    /**
     * 关注数
     */
    private long followNum;


    /**
     * 粉丝数
     */
    private long fansNum;

    /**
     * 个性签名
     */
    private String description;

    /**
     * 用户等级
     */
    private long grade;

    /**
     * 硬币数
     */
    private long coinNum;
}
