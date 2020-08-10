package pers.czj.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import pers.czj.common.CommonResult;

import java.util.Map;

/**
 * 创建在 2020/8/10 22:15
 */
@FeignClient(value = "dalidali-user",url = "/api")
public interface MessageFeignClient {

    @PostMapping("/message")
    public CommonResult addMessage(Map<String,Object> map);


}
