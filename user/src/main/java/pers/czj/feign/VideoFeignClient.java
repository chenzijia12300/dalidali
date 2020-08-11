package pers.czj.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.czj.common.CommonResult;

import java.util.Collection;
import java.util.List;

/**
 * 创建在 2020/8/10 10:55
 */
@FeignClient(value = "dalidali-video")
public interface VideoFeignClient {

    @GetMapping("/api/video/list")
    public List listBasicVideoInfoByIds(@RequestParam Collection<Long> ids);
}
