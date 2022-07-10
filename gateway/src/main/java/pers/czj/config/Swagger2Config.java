package pers.czj.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建在 2020/8/31 11:02
 */
@Configuration
@Primary
public class Swagger2Config implements SwaggerResourcesProvider {

    private static final Logger log = LoggerFactory.getLogger(Swagger2Config.class);

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public List<SwaggerResource> get() {
        discoveryClient.getServices().forEach(System.out::println);
        List<SwaggerResource> list = new ArrayList<>();
        list.add(swaggerResource("视频模块API", "/videoservice/v2/api-docs", "1.0"));
//        list.add(swaggerResource("用户模块API","/userservice/v2/api-docs","1.0"));
//        list.add(swaggerResource("评论模块API","/commentservice/v2/api-docs","1.0"));
        return list;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
