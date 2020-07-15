package pers.czj.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.UUID;

/**
 * 创建在 2020/7/13 14:38
 */
@Component
public class COSUtils {

    private static final Logger log = LoggerFactory.getLogger(COSUtils.class);

    @Autowired
    private COSClient cosClient;


    @Value("${tencent.bucket-name}")
    private String bucketName;


    /**
     * @author czj
     * 上传视频资源到COS中
     * @date 2020/7/13 15:17
     * @param [file]
     * @return java.lang.String
     */
    public String uploadFile(File file){
        log.debug("将上传的视频名:{}.视频大小:{}",file.getName(),file.length());
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,file.getName(),file);
        PutObjectResult result = cosClient.putObject(putObjectRequest);
        log.debug("上传结果："+result.getDateStr());
        return file.getName();
    }
}
