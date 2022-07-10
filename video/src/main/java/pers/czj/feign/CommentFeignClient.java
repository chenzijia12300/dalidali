package pers.czj.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 创建在 2020/12/9 19:51
 */
@FeignClient(name = "dalidali-comment")
public interface CommentFeignClient {


    @GetMapping("/crawler/{cid}/{vid}")
    public Integer crawlerDanmuList(@PathVariable("cid") long cid, @PathVariable("vid") long vid);
}
