package pers.czj.service.impl;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pers.czj.entity.Danmu;
import pers.czj.service.CrawlerDanmuService;
import pers.czj.utils.HttpUtils;
import pers.czj.utils.XmlUtils;

import java.util.List;

/**
 * 创建在 2020/12/9 9:26
 */
@Service
public class CrawlerDanmuServiceImpl implements CrawlerDanmuService {

    public static final String GET_DANMU_XML_URL = "http://comment.bilibili.com/";

    public static final String DANMU_SUFFIX = ".xml";

    private static final Logger log = LoggerFactory.getLogger(CrawlerDanmuServiceImpl.class);

    @Override
    public List<Danmu> getWebDanmuList(long cid, long vid) {
        String bodyStr = HttpUtils.syncGetStr(StrUtil.concat(false, GET_DANMU_XML_URL, String.valueOf(cid), DANMU_SUFFIX), null, null);
        List<Danmu> parse = XmlUtils.parse(bodyStr, vid);
        log.info("list:{}", parse.size());
        return parse;
    }
}
