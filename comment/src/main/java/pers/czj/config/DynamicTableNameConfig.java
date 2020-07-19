package pers.czj.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.parsers.DynamicTableNameParser;
import com.baomidou.mybatisplus.extension.parsers.ITableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.HashMap;



@Configuration
public class DynamicTableNameConfig {

    private static final Logger log = LoggerFactory.getLogger(DynamicTableNameConfig.class);

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        System.out.println("被扫描");
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        DynamicTableNameParser dynamicTableNameParser = new DynamicTableNameParser();
        dynamicTableNameParser.setTableNameHandlerMap(new HashMap<String, ITableNameHandler>(2){
            {
                put("comment",(metaObject, sql, tableName) -> {
                    String name = getParamValue("tableName",metaObject).toString();
                    log.info("评论处理的表名:{}",name);
                    return name+"_"+tableName;
                });
                put("reply",(metaObject, sql, tableName) -> {
                    String name = getParamValue("tableName",metaObject).toString();
                    log.info("回复处理的表名:{}"+name);
                    return name+"_"+tableName;
                });
/*                put("commentLog",(metaObject, sql, tableName) -> {
                    String name = getParamValue("tableName",metaObject).toString();
                    log.info("评论点赞记录处理的表名:{}"+name);
                    return name+"_"+tableName;
                });*/
            }
        });
        paginationInterceptor.setSqlParserList(Collections.singletonList(dynamicTableNameParser));
        return paginationInterceptor;
    }
    /**
     * @author czj
     * 获取参数
     * @date 2020/7/19 11:38
     * @param [title, metaObject]
     * @return java.lang.Object
     */
    private Object getParamValue(String title, MetaObject metaObject){
        RoutingStatementHandler originalObject = (RoutingStatementHandler) metaObject.getOriginalObject();
        JSONObject originalObjectJSON = JSON.parseObject(JSON.toJSONString(originalObject));
        JSONObject boundSql = originalObjectJSON.getJSONObject("boundSql");
        JSONObject parameterObject = boundSql.getJSONObject("parameterObject");
        return parameterObject.get(title);
    }
}
