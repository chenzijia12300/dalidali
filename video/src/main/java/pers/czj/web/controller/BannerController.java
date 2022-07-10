package pers.czj.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.czj.common.CommonResult;
import pers.czj.utils.BannerUtils;

/**
 * 创建在 2020/12/3 20:26
 */
@RestController
public class BannerController {


    private BannerUtils bannerUtils;

    @Autowired
    public BannerController(BannerUtils bannerUtils) {
        this.bannerUtils = bannerUtils;
    }

    @GetMapping("/banner/list")
    public CommonResult findBanner() {
        return CommonResult.success(bannerUtils.getWebBanner());
    }

}
