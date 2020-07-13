package pers.czj.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.czj.common.CommonResult;
import pers.czj.dto.CategoryOutputDto;
import pers.czj.dto.VideoDetailsOutputDto;
import pers.czj.entity.Category;
import pers.czj.exception.CategoryException;
import pers.czj.exception.VideoException;
import pers.czj.service.CategoryService;
import pers.czj.service.VideoService;

import java.util.List;

/**
 * 创建在 2020/7/11 14:19
 */
@RestController
@Api("视频分类接口")
public class CategoryController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private VideoService videoService;

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
        List<CategoryOutputDto> categoryOutputDtos = categoryService.listCategory();
        return CommonResult.success(categoryOutputDtos);
    }

    @GetMapping("/category/random/{pid}")
    @ApiOperation("获得顶级频道随机视频列表")
    public CommonResult listRandomByPId(@PathVariable("pid") long pid) throws VideoException {
        List<VideoDetailsOutputDto> list = videoService.listRandomByCategoryPId(pid);
        return CommonResult.success(list);
    }
}
