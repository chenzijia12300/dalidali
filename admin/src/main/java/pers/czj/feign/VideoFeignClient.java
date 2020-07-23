package pers.czj.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pers.czj.common.CommonResult;

import java.util.Map;

/**
 * 创建在 2020/7/23 20:48
 */
@FeignClient("dalidali-video")
public interface VideoFeignClient {

    @GetMapping("/video/audit")
    public String findNeedAuditVideo();

    @PutMapping("/video")
    public CommonResult audit(@RequestBody String json);
}
