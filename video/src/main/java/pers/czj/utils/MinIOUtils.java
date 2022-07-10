package pers.czj.utils;


import io.minio.MinioClient;
import io.minio.errors.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xmlpull.v1.XmlPullParserException;
import pers.czj.constant.HttpContentTypeEnum;

import java.io.*;
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


    @Value("${minio.bucket-video-name}")
    private String bucketVideoName;

    @Value("${minio.bucket-image-name}")
    private String bucketImageName;

    @Value("${minio.download-url}")
    private String url;


    /**
     * 上传资源到OSS服务器中,废弃原因：需要用户自己手动去closeIO流
     *
     * @param [fileName, inputStream]
     * @return java.lang.String
     * @author czj
     * @date 2020/7/16 14:32
     */
    @Deprecated
    public String uploadFile(String fileName, InputStream inputStream, HttpContentTypeEnum contentType) {
        return uploadFileByMinIO(fileName, inputStream, contentType);
    }

    /**
     * 上传文件到OSS服务器
     *
     * @param fileName    文件名
     * @param file        上传文件
     * @param contentType 上传的文件类型
     * @return java.lang.String
     * @author czj
     * @date 2020/11/28 21:19
     */
    public String uploadFile(String fileName, File file, HttpContentTypeEnum contentType) {
        try (InputStream inputStream = new FileInputStream(file)) {
            return uploadFileByMinIO(fileName, inputStream, contentType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private String uploadFileByMinIO(String fileName, InputStream inputStream, HttpContentTypeEnum contentType) {
        log.debug("将上传的文件名:{}", fileName);
        String bucketName = null;
        switch (contentType) {
            case MP4:
                bucketName = bucketVideoName;
                break;
            case JPEG:
                bucketName = bucketImageName;
                break;
        }
        try {
            minioClient.putObject(bucketName, fileName, inputStream, contentType.getContentType());
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


    /**
     * 将文件保存到本地
     *
     * @param fileName 文件名 例如 红莉栖.jpg
     * @param destPath 保存路径 例如 C:\Users\ZJ\Desktop\
     * @author czj
     * @date 2020/7/23 19:08
     */
    public void saveVideoLocalTemp(String fileName, String destPath) {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(minioClient.getObject(bucketImageName, fileName));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(destPath, fileName)))
        ) {
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = bufferedInputStream.read(bytes)) != -1) {
                bufferedOutputStream.write(bytes, 0, len);
            }
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
    }
}
