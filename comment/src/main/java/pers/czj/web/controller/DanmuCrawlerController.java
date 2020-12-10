package pers.czj.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.czj.common.CommonResult;
import pers.czj.entity.Danmu;
import pers.czj.service.CrawlerDanmuService;
import pers.czj.service.DanmuService;

import java.util.List;

/**
 * 创建在 2020/12/9 18:59
 */
@RestController
@RequestMapping("/crawler")
public class DanmuCrawlerController {

    private static final Logger log = LoggerFactory.getLogger(DanmuCrawlerController.class);

    private CrawlerDanmuService crawlerDanmuService;

    private DanmuService danmuService;


    @Autowired
    public DanmuCrawlerController(CrawlerDanmuService crawlerDanmuService, DanmuService danmuService) {
        this.crawlerDanmuService = crawlerDanmuService;
        this.danmuService = danmuService;
    }

    @GetMapping("/{cid}/{vid}")
    public Integer crawlerDanmuList(@PathVariable("cid") long cid,@PathVariable("vid") long vid){
        List<Danmu> webDanmuList = crawlerDanmuService.getWebDanmuList(cid, vid);
        log.info("list:{}",webDanmuList);
        danmuService.saveBatch(webDanmuList);
        return webDanmuList.size();
    }



}
