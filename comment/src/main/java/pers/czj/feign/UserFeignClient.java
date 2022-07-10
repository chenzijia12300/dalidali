package pers.czj.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.czj.common.CommonResult;
import pers.czj.dto.NumberInputDto;

import java.util.Map;

/**
 * 创建在 2020/11/1 14:37
 */
@FeignClient(value = "dalidali-user")
@RequestMapping("/api")
public interface UserFeignClient {

    @PutMapping("/dynamic/comment")
    public CommonResult incrCommentNum(@RequestBody NumberInputDto dto);

    @PostMapping("/message")
    public CommonResult addMessage(Map<String, Object> map);
}
