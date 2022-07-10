package pers.czj.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 创建在 2020/3/19 20:39
 */
@Configuration
public class URLConfig implements WebMvcConfigurer {
    @Value("${apk.path-mapping}")
    private String apkPathMapping;

    private String dir;


    public URLConfig(@Value("${apk.path-mapping}") String apkPathMapping) {
        this.dir = System.getProperty("user.dir") + apkPathMapping;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(apkPathMapping + "**").addResourceLocations("file:" + dir);
    }

}
