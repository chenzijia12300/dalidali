package pers.czj.web.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.czj.common.CommonResult;
import pers.czj.entity.ApkInfo;
import pers.czj.service.ApkInfoService;
import pers.czj.util.HostAddressUtils;
import pers.czj.utils.MinIOUtils;

import java.io.File;
import java.io.IOException;

/**
 * 创建在 2020/9/22 20:44
 */
@RestController
@CrossOrigin
@Api(tags = "apk接口")
public class ApkController {

    private static final Logger log = LoggerFactory.getLogger(ApkController.class);

    private String dir;

    private HostAddressUtils hostAddressUtils;

    private ApkInfoService apkInfoService;

    private String apkDir;

    private MinIOUtils minIOUtils;

    @Autowired
    public ApkController(HostAddressUtils hostAddressUtils, ApkInfoService apkInfoService,@Value("${apk.path-mapping}") String apkPathMapping,MinIOUtils minIOUtils) {
        this.dir = System.getProperty("user.dir")+apkPathMapping;
        this.hostAddressUtils = hostAddressUtils;
        this.apkInfoService = apkInfoService;
        this.apkDir = apkPathMapping;
        this.minIOUtils = minIOUtils;
        log.info("dir:{}",dir);
    }


    @ApiOperation("APK上传接口")
    @PostMapping(value = "/apk/upload",consumes = "multipart/form-data")
    public CommonResult uploadApk(@RequestParam("file") @ApiParam MultipartFile file) throws IOException {
        //视频存储到本地
        String filename = file.getOriginalFilename();
        log.info("APK版本号:{}",filename);
/*
        File temp = new File(dir,filename);
        if (temp.exists()){
            log.info("APK版本号:{}覆盖",filename);
            temp.delete();
        }
        try {
            file.transferTo(temp);
            log.info("移动端文件:[{}]上传成功！",temp.getPath());
        } catch (IOException e) {
            log.error("存储服务器本地出现问题:{},错误:{}",file.getName(),e.getMessage());
        }*/

        return CommonResult.success("上传APK文件成功！",minIOUtils.uploadAPK(filename,file.getInputStream()));
    }


/*    @RequestMapping(value = "/apk",method = {RequestMethod.PUT,RequestMethod.POST})
    public CommonResult addOrUpdateApkInfo(@RequestBody ApkInfo apkInfo){
        boolean flag = apkInfoService.saveOrUpdate(apkInfo,new UpdateWrapper<ApkInfo>().eq("version",apkInfo.getVersion()));
        log.info("添加/更新版本:[{}]移动端信息成功！",apkInfo.getVersion());
        return CommonResult.success(StrUtil.format("添加/更新版本:[{}]移动端信息成功！",apkInfo.getVersion()));
    }*/


    @RequestMapping(value = "/apk",method = {RequestMethod.PUT,RequestMethod.POST})
    public CommonResult addOrUpdateApkInfo(@RequestParam String version,@RequestParam String description,@RequestParam MultipartFile file) throws IOException {


        String fileName = version+".apk";
        ApkInfo apkInfo = new ApkInfo();
        apkInfo.setUrl(minIOUtils.uploadAPK(fileName,file.getInputStream()));
        apkInfo.setVersion(version);
        apkInfo.setDescription(description);
        apkInfoService.saveOrUpdate(apkInfo,new UpdateWrapper<ApkInfo>().eq("version",apkInfo.getVersion()));
        log.info("添加/更新版本:[{}]移动端信息成功！",apkInfo.getVersion());
        return CommonResult.success(StrUtil.format("添加/更新版本:[{}]移动端信息成功！",apkInfo.getVersion()));
    }

    @GetMapping("/apk")
    @ApiOperation("获得移动端最新版本信息")
    public CommonResult findNewestApkInfo(){
        ApkInfo apkInfo = apkInfoService.getOne(new QueryWrapper<ApkInfo>().orderByDesc("create_time").last("LIMIT 1"));
        return CommonResult.success(apkInfo);
    }

}
