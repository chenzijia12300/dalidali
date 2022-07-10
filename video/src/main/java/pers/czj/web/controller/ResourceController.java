package pers.czj.web.controller;

import cn.hutool.core.io.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pers.czj.common.CommonResult;
import pers.czj.constant.HttpContentTypeEnum;
import pers.czj.exception.VideoException;
import pers.czj.utils.FileUtils;
import pers.czj.utils.MinIOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 创建在 2020/10/24 19:00
 */
@RestController
public class ResourceController {

    private static final Logger log = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private MinIOUtils minIOUtils;

    @GetMapping("/upload/img")
    public CommonResult uploadImg(@RequestParam("file") MultipartFile file) throws VideoException, FileNotFoundException {
        File temp = FileUtils.saveLocalFile(file);
        String imgWebUrl = minIOUtils.uploadFile(temp.getName(), new FileInputStream(temp), HttpContentTypeEnum.JPEG);
        FileUtil.del(temp);
        return CommonResult.success(imgWebUrl);
    }


}
