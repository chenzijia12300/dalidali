package pers.czj.service;

import java.util.Map;

/**
 * 创建在 2020/12/2 20:19
 */
public interface VideoInfoCrawlerService {


    /**
     * 通过访问B站API,获取视频的基本信息
     * @author czj
     * @date 2020/12/2 20:20
     * @param url 视频路径
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String,String> getVideoBasicInfo(String url);

}
