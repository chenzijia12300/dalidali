package pers.czj.utils;


import io.minio.MinioClient;
import io.minio.errors.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 创建在 2020/7/13 14:38
 */
@Component
public class MinIOUtils {

    private static final Logger log = LoggerFactory.getLogger(MinIOUtils.class);

    @Autowired
    private MinioClient minioClient;


    @Value("${minio.bucket-name}")
    private String bucketName;

    @Value("${minio.download-url}")
    private String url;

    /**
     * 上传APK资源到MinIO中
     *
     * @param [fileName, inputStream]
     * @return java.lang.String
     * @author czj
     * @date 2020/7/16 14:32
     */
    public String uploadAPK(String fileName, InputStream inputStream) {
        try {
            minioClient.putObject(bucketName, fileName, inputStream, "application/octet-stream");
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoResponseException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
        return url + bucketName + "/" + fileName;
    }
}
