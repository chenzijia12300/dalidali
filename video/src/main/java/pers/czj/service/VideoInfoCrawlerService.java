package pers.czj.service;

import java.util.List;
import java.util.Map;

/**
 * 创建在 2020/12/2 20:19
 */
public interface VideoInfoCrawlerService {


    /**
     * 通过访问B站API,获取视频的基本信息
     *
     * @param url 视频路径
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author czj
     * @date 2020/12/2 20:20
     */
    public Map<String, String> getVideoBasicInfo(String url);


    /**
     * 通过访问B站API，获得频道下推荐视频列表
     *
     * @param [rid]
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     * @author czj
     * @date 2020/12/7 15:09
     */
    public List<Map<String, String>> getChannelRecommendVideo(int rid);
}
