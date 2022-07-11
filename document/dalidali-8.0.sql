/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80015
Source Host           : 127.0.0.1:3306
Source Database       : dalidali

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2022-07-10 19:06:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for apk_info
-- ----------------------------
DROP TABLE IF EXISTS `apk_info`;
CREATE TABLE `apk_info` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `version` varchar(255) NOT NULL,
                            `description` varchar(255) NOT NULL,
                            `url` varchar(255) NOT NULL,
                            `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分类主键',
                            `name` varchar(32) NOT NULL COMMENT '分类名字',
                            `pid` bigint(20) NOT NULL DEFAULT '0' COMMENT '父分类的ID',
                            `pro_num` bigint(20) NOT NULL DEFAULT '0' COMMENT '频道总数',
                            `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '频道图标地址',
                            `priority` int(11) NOT NULL DEFAULT '0' COMMENT '频道显示优先级',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for danmu
-- ----------------------------
DROP TABLE IF EXISTS `danmu`;
CREATE TABLE `danmu` (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `vid` bigint(20) NOT NULL COMMENT '视频主键',
                         `show_second` bigint(20) NOT NULL COMMENT '显示在第几秒',
                         `uid` bigint(20) NOT NULL COMMENT '用户主键',
                         `location` tinyint(4) NOT NULL COMMENT '显示位置',
                         `content` varchar(255) NOT NULL COMMENT '内容',
                         `color` varchar(255) NOT NULL DEFAULT '#000000',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2652 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for dynamic
-- ----------------------------
DROP TABLE IF EXISTS `dynamic`;
CREATE TABLE `dynamic` (
                           `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '动态主键',
                           `uid` bigint(20) NOT NULL COMMENT '发送动态的用户主键',
                           `oid` bigint(20) NOT NULL COMMENT '对应视频/专栏的主键',
                           `type` tinyint(4) NOT NULL COMMENT '动态类型',
                           `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           `imgs` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                           `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                           `praise_num` bigint(20) NOT NULL,
                           `comment_num` bigint(20) NOT NULL,
                           `forward_num` bigint(20) NOT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for dynamic_comment
-- ----------------------------
DROP TABLE IF EXISTS `dynamic_comment`;
CREATE TABLE `dynamic_comment` (
                                   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论主键',
                                   `pid` bigint(20) NOT NULL COMMENT '视频/专栏的主键',
                                   `uid` bigint(20) NOT NULL COMMENT '评论人主键',
                                   `praise_num` bigint(20) NOT NULL COMMENT '点赞数',
                                   `reply_num` bigint(20) NOT NULL COMMENT '回复数',
                                   `content` varchar(255) NOT NULL COMMENT '评论内容',
                                   `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for dynamic_comment_log
-- ----------------------------
DROP TABLE IF EXISTS `dynamic_comment_log`;
CREATE TABLE `dynamic_comment_log` (
                                       `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                       `cid` bigint(20) NOT NULL COMMENT '评论主键',
                                       `uid` bigint(20) NOT NULL COMMENT '用户主键',
                                       PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for dynamic_copy
-- ----------------------------
DROP TABLE IF EXISTS `dynamic_copy`;
CREATE TABLE `dynamic_copy` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '动态主键',
                                `uid` bigint(20) NOT NULL COMMENT '发送动态的用户主键',
                                `oid` bigint(20) NOT NULL COMMENT '对应视频/专栏的主键',
                                `type` tinyint(4) NOT NULL COMMENT '动态类型',
                                `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                `imgs` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                `praise_num` bigint(20) NOT NULL,
                                `comment_num` bigint(20) NOT NULL,
                                `forward_num` bigint(20) NOT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for dynamic_log
-- ----------------------------
DROP TABLE IF EXISTS `dynamic_log`;
CREATE TABLE `dynamic_log` (
                               `id` bigint(20) NOT NULL,
                               `uid` bigint(20) NOT NULL,
                               `did` bigint(20) NOT NULL,
                               `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               `is_praise` tinyint(4) NOT NULL,
                               `is_forward` tinyint(4) NOT NULL,
                               `is_collection` tinyint(4) NOT NULL,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for dynamic_reply
-- ----------------------------
DROP TABLE IF EXISTS `dynamic_reply`;
CREATE TABLE `dynamic_reply` (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '回复主键',
                                 `cid` bigint(20) NOT NULL,
                                 `ruid` bigint(20) NOT NULL DEFAULT '0' COMMENT '回复目标的用户的主键',
                                 `uid` bigint(20) NOT NULL COMMENT '回复人主键',
                                 `praise_num` bigint(20) NOT NULL COMMENT '点赞数',
                                 `content` varchar(255) NOT NULL COMMENT '回复内容',
                                 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 PRIMARY KEY (`id`),
                                 KEY `COMMENT_ID` (`cid`),
                                 KEY `USER_ID` (`uid`),
                                 KEY `REPLY_USER_ID` (`ruid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for dynamic_reply_log
-- ----------------------------
DROP TABLE IF EXISTS `dynamic_reply_log`;
CREATE TABLE `dynamic_reply_log` (
                                     `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                     `rid` bigint(20) NOT NULL COMMENT '回复主键',
                                     `uid` bigint(20) NOT NULL COMMENT '用户主键',
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for follow
-- ----------------------------
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '关注主键',
                          `uid` bigint(20) NOT NULL COMMENT '关注人',
                          `fuid` bigint(20) NOT NULL COMMENT '被关注人',
                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '关注时间',
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
                           `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '消息主键',
                           `send_uid` bigint(20) NOT NULL COMMENT '发送用户主键',
                           `receive_uid` bigint(20) NOT NULL COMMENT '接收者主键',
                           `type` tinyint(4) NOT NULL COMMENT '动作类型',
                           `content` varchar(512) DEFAULT NULL COMMENT '内容',
                           `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for old_dynamic
-- ----------------------------
DROP TABLE IF EXISTS `old_dynamic`;
CREATE TABLE `old_dynamic` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '动态主键',
                               `uid` bigint(20) NOT NULL COMMENT '发送动态的用户主键',
                               `oid` bigint(20) NOT NULL COMMENT '对应视频/专栏的主键',
                               `type` tinyint(4) NOT NULL COMMENT '动态类型',
                               `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for play_num_tab
-- ----------------------------
DROP TABLE IF EXISTS `play_num_tab`;
CREATE TABLE `play_num_tab` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '播放量流水表主键',
                                `vid` bigint(20) NOT NULL COMMENT '视频主键',
                                `num` bigint(20) NOT NULL COMMENT '播放量',
                                `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '对应时间',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=903 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for post_comment
-- ----------------------------
DROP TABLE IF EXISTS `post_comment`;
CREATE TABLE `post_comment` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论主键',
                                `pid` bigint(20) NOT NULL COMMENT '视频/专栏的主键',
                                `uid` bigint(20) NOT NULL COMMENT '评论人主键',
                                `praise_num` bigint(20) NOT NULL COMMENT '点赞数',
                                `reply_num` bigint(20) NOT NULL COMMENT '回复数',
                                `content` varchar(255) NOT NULL COMMENT '评论内容',
                                `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                        `account` varchar(12) NOT NULL COMMENT '账户',
                        `password` varchar(12) NOT NULL COMMENT '密码',
                        `username` varchar(48) NOT NULL COMMENT '用户名',
                        `email` varchar(30) NOT NULL DEFAULT '' COMMENT '用户邮箱',
                        `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户头像',
                        `follow_num` bigint(20) NOT NULL DEFAULT '0' COMMENT '关注数',
                        `fans_num` bigint(20) NOT NULL DEFAULT '0' COMMENT '粉丝数',
                        `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '个人描述',
                        `grade` bigint(20) NOT NULL,
                        `coin_num` bigint(20) NOT NULL COMMENT '硬币数',
                        `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更改时间',
                        `read_dynamic_time` datetime DEFAULT CURRENT_TIMESTAMP,
                        `read_message_time` datetime DEFAULT CURRENT_TIMESTAMP,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `USER_ID` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user_collection_log
-- ----------------------------
DROP TABLE IF EXISTS `user_collection_log`;
CREATE TABLE `user_collection_log` (
                                       `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                       `uid` bigint(20) NOT NULL,
                                       `vid` bigint(20) NOT NULL,
                                       `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                       PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video` (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
                         `uid` bigint(20) NOT NULL COMMENT '作者主键',
                         `play_num` bigint(20) NOT NULL DEFAULT '0' COMMENT '视频播放量',
                         `danmu_num` bigint(20) NOT NULL DEFAULT '0' COMMENT '弹幕数',
                         `comment_num` bigint(20) NOT NULL DEFAULT '0' COMMENT '评论数',
                         `praise_num` bigint(20) NOT NULL DEFAULT '0' COMMENT '点赞数',
                         `coin_num` bigint(20) NOT NULL DEFAULT '0' COMMENT '硬币数',
                         `category_p_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '视频的顶级父频道',
                         `category_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '视频所属频道',
                         `category_p_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '视频所属频道字符串（冗余）',
                         `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '视频所属频道字符串（冗余）',
                         `title` varchar(255) NOT NULL COMMENT '视频标题',
                         `urls` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '视频的路径们',
                         `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '视频简介',
                         `tags` varchar(255) NOT NULL COMMENT '视频标签们',
                         `resolution_state` int(11) NOT NULL COMMENT '视频分辨率',
                         `publish_state` int(11) NOT NULL COMMENT '视频发布状态',
                         `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '视频创建时间',
                         `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '视频更改时间',
                         `length` bigint(20) DEFAULT NULL COMMENT '视频时长',
                         `preview_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '预览图',
                         `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '视频封面',
                         `width` int(11) DEFAULT NULL,
                         `height` int(11) DEFAULT NULL,
                         `screen_type` tinyint(4) NOT NULL,
                         `compress_cover` varchar(255) DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for video_comment
-- ----------------------------
DROP TABLE IF EXISTS `video_comment`;
CREATE TABLE `video_comment` (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论主键',
                                 `pid` bigint(20) NOT NULL COMMENT '视频/专栏的主键',
                                 `uid` bigint(20) NOT NULL COMMENT '评论人主键',
                                 `praise_num` bigint(20) NOT NULL COMMENT '点赞数',
                                 `reply_num` bigint(20) NOT NULL COMMENT '回复数',
                                 `content` varchar(255) NOT NULL COMMENT '评论内容',
                                 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for video_comment_log
-- ----------------------------
DROP TABLE IF EXISTS `video_comment_log`;
CREATE TABLE `video_comment_log` (
                                     `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                     `cid` bigint(20) NOT NULL COMMENT '评论主键',
                                     `uid` bigint(20) NOT NULL COMMENT '用户主键',
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for video_crawler_log
-- ----------------------------
DROP TABLE IF EXISTS `video_crawler_log`;
CREATE TABLE `video_crawler_log` (
                                     `id` int(11) NOT NULL AUTO_INCREMENT,
                                     `title` varchar(255) NOT NULL,
                                     `url` varchar(255) NOT NULL,
                                     `time` bigint(20) NOT NULL DEFAULT '0',
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=216 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for video_crawler_log_copy
-- ----------------------------
DROP TABLE IF EXISTS `video_crawler_log_copy`;
CREATE TABLE `video_crawler_log_copy` (
                                          `id` int(11) NOT NULL AUTO_INCREMENT,
                                          `title` varchar(255) NOT NULL,
                                          `url` varchar(255) NOT NULL,
                                          `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for video_log
-- ----------------------------
DROP TABLE IF EXISTS `video_log`;
CREATE TABLE `video_log` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT,
                             `uid` bigint(20) NOT NULL COMMENT '用户主键',
                             `is_praise` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否点赞',
                             `coin_num` tinyint(4) NOT NULL DEFAULT '0' COMMENT '硬币数',
                             `is_collection` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否收藏',
                             `vid` bigint(20) NOT NULL COMMENT '视频主键',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for video_recommend
-- ----------------------------
DROP TABLE IF EXISTS `video_recommend`;
CREATE TABLE `video_recommend` (
                                   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                   `vid` bigint(20) NOT NULL COMMENT '视频主键',
                                   `status` bit(1) NOT NULL COMMENT '推荐状态',
                                   `sort` int(11) NOT NULL COMMENT '排序',
                                   `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   `end_time` datetime DEFAULT NULL,
                                   `location` int(11) NOT NULL,
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for video_reply
-- ----------------------------
DROP TABLE IF EXISTS `video_reply`;
CREATE TABLE `video_reply` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '回复主键',
                               `cid` bigint(20) NOT NULL,
                               `ruid` bigint(20) NOT NULL DEFAULT '0' COMMENT '回复目标的用户的主键',
                               `uid` bigint(20) NOT NULL COMMENT '回复人主键',
                               `praise_num` bigint(20) NOT NULL COMMENT '点赞数',
                               `content` varchar(255) NOT NULL COMMENT '回复内容',
                               `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               PRIMARY KEY (`id`),
                               KEY `COMMENT_ID` (`cid`),
                               KEY `USER_ID` (`uid`),
                               KEY `REPLY_USER_ID` (`ruid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for video_reply_log
-- ----------------------------
DROP TABLE IF EXISTS `video_reply_log`;
CREATE TABLE `video_reply_log` (
                                   `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                   `rid` bigint(20) NOT NULL COMMENT '回复主键',
                                   `uid` bigint(20) NOT NULL COMMENT '用户主键',
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
