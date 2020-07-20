package pers.czj.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import pers.czj.common.CommonResult;
import pers.czj.common.User;
import pers.czj.dto.UserCollectionLogInputDto;

/**
 * 创建在 2020/7/20 11:36
 */

@FeignClient(name = "dalidali-user")
public interface UserFeignClient {

    @GetMapping("/user/coin/{id}")
    public int findCoinNumById(@PathVariable("id") long id);

    @PostMapping("/user/coin")
    public int incrCoinNumById(long id,int num);

    @PostMapping("/collection/dynamic")
    public CommonResult dynamicCollection(@RequestBody UserCollectionLogInputDto dto);
/*    @PostMapping("/collection/video")
    public CommonResult addCollectionLog(@RequestBody UserCollectionLogInputDto dto);

    @DeleteMapping("/collection/video/{id}")
    public CommonResult deleteCollectionLog(@PathVariable("id") long id);*/
}