package pers.czj.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pers.czj.common.CommonResult;
import pers.czj.dto.NumberInputDto;

/**
 * 创建在 2020/7/30 17:55
 */
@FeignClient("dalidali-video")
public interface VideoFeignClient {

    @PostMapping("/video/comment")
    public CommonResult incrCommentNum(@RequestBody NumberInputDto dto);

    @PostMapping("/video/danmu")
    public CommonResult incrDanmuNum(@RequestBody NumberInputDto dto);
}
