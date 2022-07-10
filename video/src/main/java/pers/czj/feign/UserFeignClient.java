package pers.czj.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import pers.czj.common.CommonResult;
import pers.czj.dto.UserCollectionLogInputDto;
import pers.czj.feign.fallback.UserFallback;

import java.util.Map;

/**
 * 创建在 2020/7/20 11:36
 */

@FeignClient(name = "dalidali-user", fallback = UserFallback.class)
public interface UserFeignClient {

    @GetMapping("/user/coin/{id}")
    public int findCoinNumById(@PathVariable("id") long id);

    @PostMapping("/user/coin")
    public CommonResult incrCoinNumById(@RequestParam long id, @RequestParam int num);

    @PostMapping("/collection/dynamic")
    public CommonResult dynamicCollection(@RequestBody UserCollectionLogInputDto dto);
/*    @PostMapping("/collection/video")
    public CommonResult addCollectionLog(@RequestBody UserCollectionLogInputDto dto);

    @DeleteMapping("/collection/video/{id}")
    public CommonResult deleteCollectionLog(@PathVariable("id") long id);*/

    @PostMapping("/api/dynamic")
    public CommonResult addDynamic(@RequestBody Map<String, Object> map);


    @GetMapping("/user/basic/{uid}/{followerUserId}")
    public Map<String, Object> findBasicInfoById(@PathVariable("uid") long uid, @PathVariable("followerUserId") long followerUserId);

    @PostMapping("/user/create")
    public Long createUserIfNeeded(@RequestBody Map<String, String> map);
}
