package pers.czj.web.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.czj.common.CommonResult;
import pers.czj.dto.CategoryOutputDto;
import pers.czj.dto.VideoBasicOutputDto;
import pers.czj.dto.VideoDetailsOutputDto;
import pers.czj.entity.Category;
import pers.czj.exception.CategoryException;
import pers.czj.exception.VideoException;
import pers.czj.service.CategoryService;
import pers.czj.service.VideoService;
import pers.czj.utils.RedisUtils;
import sun.nio.ch.IOUtil;

import javax.net.ssl.HttpsURLConnection;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * 创建在 2020/7/11 14:19
 */
@RestController
@Api(tags = "视频分类接口")
@CrossOrigin
@Validated
public class CategoryController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private RedisUtils redisUtils;

    @Value("${redis.category-list}")
    private String categoryListKey;

    @PostMapping("/category")
    @ApiOperation("添加视频分类")
    public CommonResult addCategory(@RequestBody @Validated Category category) throws CategoryException {
        boolean flag = categoryService.save(category);
        if (!flag){
            throw new CategoryException("遇到未知原因，添加视频分类失败");
        }
        log.info("管理员:{}添加:{}分类","TOM",category.getName());
        return CommonResult.success();
    }


    @GetMapping("/category")
    @ApiOperation("获得视频总分类")
    public CommonResult listCategory() throws CategoryException {
        List list = null;
        if (redisUtils.hasKey(categoryListKey)){
            log.debug("分类走缓存");
            list = redisUtils.getList(categoryListKey);
        }else {
            list = categoryService.listCategory();
            redisUtils.pushList(categoryListKey,list);
        }
        return CommonResult.success(list);
    }

    @GetMapping("/category/random/{pid}")
    @ApiOperation("获得顶级频道随机视频列表")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "pid",paramType = "path",value = "顶级频道主键"))
    public CommonResult listRandomByPId(@PathVariable("pid") long pid) throws VideoException {
        List<VideoBasicOutputDto> list = videoService.listRandomByCategoryPId(pid);
        return CommonResult.success(list);
    }

    @GetMapping(value = {"/category/random/all/{pageSize}","/category/random/all"})
    @ApiOperation("移动端【获得首页视频信息列表】接口")
    public CommonResult listRandomAll(@Max(8) @PathVariable(value = "pageSize",required = false)Integer pageSize){
        List<VideoBasicOutputDto> list = videoService.listSlowRandomAll(pageSize==null?8:pageSize);
        return CommonResult.success(list);
    }

    public static void main(String[] args) throws IOException {
        HttpsURLConnection connection = (HttpsURLConnection) new URL("https://p2.music.126.net/EaQwfojYMZ66xomdTrIRvA==/6006632022768749.jpg").openConnection();
        connection.setDoInput(true);
        connection.connect();
        InputStream inputStream = connection.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\ZJ\\Desktop\\qwe.jpg");
        int len = -1;
        byte [] bytes = new byte[1024];
        while((len = inputStream.read(bytes))!=-1){
            fileOutputStream.write(bytes,0,len);
        }
        fileOutputStream.close();
        connection.disconnect();
    }
}
