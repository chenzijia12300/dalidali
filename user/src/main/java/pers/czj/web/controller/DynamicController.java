package pers.czj.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import pers.czj.common.CommonResult;
import pers.czj.entity.Dynamic;
import pers.czj.feign.VideoFeignClient;
import pers.czj.service.DynamicService;
import pers.czj.service.UserService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 创建在 2020/8/9 22:43
 */
@RestController
@Api(tags = "个人动态接口")
public class DynamicController {

    private DynamicService dynamicService;

    private VideoFeignClient videoFeignClient;

    private UserService userService;

    @Autowired
    public DynamicController(DynamicService dynamicService, VideoFeignClient videoFeignClient, UserService userService) {
        this.dynamicService = dynamicService;
        this.videoFeignClient = videoFeignClient;
        this.userService = userService;
    }

    @GetMapping("/dynamic/list/{pageNum}/{pageSize}")
    @ApiOperation("获得动态列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第X页（X>=1）", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "X个数据（X<=8）", paramType = "path")
    }
    )
    public CommonResult listDynamic(@RequestParam long uid,
                                    @PathVariable("pageNum")@Min(1) int pageNum,
                                    @PathVariable("pageSize")@Max(8) int pageSize){
        /**
            查询相应动态,初始化所需参数
         */
        List<Dynamic> dynamics = dynamicService.listDynamicByPage(uid,pageNum,pageSize);
        List<Long> videoIds = new ArrayList<>();
        List<Long> postIds = new ArrayList<>();
        List videoList = null;

        /**
         *  循环拆分出不同类型的动态（视频/专栏/个人）
         */
        dynamics.forEach(dynamic -> {
            switch (dynamic.getType()){
                case VIDEO:
                    videoIds.add(dynamic.getOid());
                    break;
                case POST:
                    postIds.add(dynamic.getOid());
                    break;
                default:
                    break;
            }
        });
        if (!CollectionUtils.isEmpty(videoIds)) {
            videoList = videoFeignClient.listBasicVideoInfoByIds(videoIds);
        }

        if (!CollectionUtils.isEmpty(postIds)){
            // do something
        }
        //更新最后阅读个人动态时间
        userService.updateLastReadDynamicTime(uid,new Date());
        return CommonResult.success(videoList);
    }

    @GetMapping("/dynamic/unread")
    @ApiOperation("获得个人动态未读总数")
    public CommonResult findUnreadDynamicCount(@RequestParam long uid){
        return CommonResult.success(dynamicService.findUnreadCount(uid));
    }
}
