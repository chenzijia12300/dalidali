package pers.czj.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.czj.common.CommonResult;
import pers.czj.feign.VideoFeignClient;

import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 创建在 2020/7/23 21:40
 */
@RestController
@Api("管理员控制器")
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(10,15,10, TimeUnit.SECONDS,new LinkedBlockingDeque<>());

    @Autowired
    private VideoFeignClient videoFeignClient;


    @GetMapping("/admin/audit/video")
    public CommonResult findNeedAuditVideo(){
        String str = videoFeignClient.findNeedAuditVideo();
        log.info("str:{}",str);
        return CommonResult.success(str,null);
    }

    @PostMapping("/admin/audit/video")
    public CommonResult audit(@RequestBody String str){
        JSONObject jsonObject = JSON.parseObject(str);
        executor.execute(()->videoFeignClient.audit(str));
        return CommonResult.success("提交审核成功~,视频处理中");
    }
}
