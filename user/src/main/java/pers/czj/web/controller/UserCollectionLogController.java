package pers.czj.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.czj.common.CommonResult;
import pers.czj.dto.UserCollectionLogInputDto;
import pers.czj.entity.UserCollectionLog;
import pers.czj.exception.UserException;
import pers.czj.service.UserCollectionLogService;

/**
 * 创建在 2020/7/20 22:43
 */
@RestController
@Api(tags = "用户收藏接口")
public class UserCollectionLogController {

    private static final Logger log = LoggerFactory.getLogger(UserCollectionLogController.class);

    @Autowired
    private UserCollectionLogService logService;

/*
    @PostMapping("/collection/video")
    @ApiOperation("收藏视频")
    public CommonResult addLog(@RequestBody @Validated UserCollectionLogInputDto dto) throws UserException {
        UserCollectionLog log = new UserCollectionLog();
        BeanUtils.copyProperties(log,dto);
        boolean flag = logService.save(log);
        if (!flag){
            throw new UserException("收藏视频失败，请重试~");
        }
        return CommonResult.success();
    }

    @DeleteMapping("/collection/video/{uid}/{vid}")
    @ApiOperation("删除收藏记录")
    public CommonResult deleteLog(@PathVariable("uid") long uid,@PathVariable("vid") long vid) throws UserException {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid",uid);
        queryWrapper.eq("vid",vid);
        boolean flag = logService.remove(queryWrapper);
        if (!flag){
            throw new UserException("删除收藏记录失败，请重试~");
        }
        return CommonResult.success();
    }*/

    @PostMapping("/collection/dynamic")
    public CommonResult dynamicCollection(@RequestBody @Validated UserCollectionLogInputDto dto){
        return CommonResult.success(logService.dynamicLog(dto.getUid(),dto.getVid(),dto.isAdd()));
    }
}
