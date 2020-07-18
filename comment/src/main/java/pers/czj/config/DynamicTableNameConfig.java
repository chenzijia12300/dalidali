package pers.czj.config;

import com.baomidou.mybatisplus.extension.parsers.DynamicTableNameParser;
import com.baomidou.mybatisplus.extension.parsers.ITableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;

/**
 * 创建在 2020/7/18 23:45
 */
@Configuration
public class DynamicTableNameConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        System.out.println("被扫描");
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        DynamicTableNameParser dynamicTableNameParser = new DynamicTableNameParser();
        dynamicTableNameParser.setTableNameHandlerMap(new HashMap<String, ITableNameHandler>(){
            {
                put("comment",(metaObject, sql, tableName) -> {
                    String[] names = metaObject.getGetterNames();
                    for(String str:names){
                        System.out.println(str);
                    }
                    /*System.out.println(dynamicName);*/
                    return tableName;
                });
            }
        });
        paginationInterceptor.setSqlParserList(Collections.singletonList(dynamicTableNameParser));
        return paginationInterceptor;
    }
}
