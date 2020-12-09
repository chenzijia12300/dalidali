package pers.czj.service;

import pers.czj.entity.Danmu;

import java.util.List;

/**
 * 创建在 2020/12/9 9:21
 */
public interface CrawlerDanmuService {

    /**
     * 爬取视频对应弹幕列表
     * @author czj
     * @date 2020/12/9 9:22
     * @param cid 视频1P CID
     * @param vid 视频主键
     * @return java.util.List<pers.czj.entity.Danmu>
     */
    public List<Danmu> getWebDanmuList(long cid,long vid);
}
