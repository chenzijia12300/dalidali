package pers.czj.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.czj.common.CommonResult;
import pers.czj.utils.GetChannelDataUtils;

/**
 * 创建在 2020/11/30 17:20
 */
@RestController
@RequestMapping("/crawler")
public class ChannelCrawlerController {

    private static final Logger log = LoggerFactory.getLogger(ChannelCrawlerController.class);

    private GetChannelDataUtils channelDataUtils;


    @Autowired
    public ChannelCrawlerController(GetChannelDataUtils channelDataUtils) {
        this.channelDataUtils = channelDataUtils;
    }


    @GetMapping("/channel")
    public CommonResult crawlerChannel(){
        channelDataUtils.addChannelsInfoByWeb();
        return CommonResult.success();
    }
}
