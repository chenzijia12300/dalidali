package pers.czj.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 创建在 2020/7/13 14:26
 */
@Configuration
public class COSConfig {

    @Value("${tencent.secretId}")
    private String secretId;

    @Value("${tencent.secretKey}")
    private String secretKey;

    @Value("${tencent.bucket}")
    private String bucket;

    @Bean
    public COSClient cosClient(){
        COSCredentials cosCredentials = new BasicCOSCredentials(secretId,secretKey);
        Region region = new Region(bucket);
        ClientConfig clientConfig = new ClientConfig(region);
        COSClient cosClient = new COSClient(cosCredentials,clientConfig);
        return cosClient;
    }
}
