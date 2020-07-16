package pers.czj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Profile;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 创建在 2020/7/11 14:50
 */
@EnableRedisHttpSession
/*@EnableEurekaClient*/
@SpringBootApplication
@MapperScan("pers.czj.mapper")
@Profile("dev")
public class VideoApplication {
    public static void main(String[] args) {
        SpringApplication.run(VideoApplication.class,args);
    }
}
