package pers.czj.feign;

import org.springframework.web.bind.annotation.RequestBody;
import pers.czj.common.CommonResult;

import java.util.Map;

/**
 * 创建在 2020/8/10 11:34
 */
public interface DynamicFeignClient {

    public CommonResult addDynamic(@RequestBody Map<String, Object> map);
}
