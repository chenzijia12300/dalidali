package pers.czj.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 创建在 2020/9/23 22:31
 */
@Component
public class HostAddressUtils {

    private static final Logger log = LoggerFactory.getLogger(HostAddressUtils.class);


    private String serverUrl;



    public HostAddressUtils(@Value("#{':' + ${server.port} + '/'}") String port,
                            @Value("${server.protocol:http://}") String protocol) {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String address = inetAddress.getHostAddress();
            serverUrl = protocol+address+port;
            log.info("服务器地址:{}",serverUrl);
        } catch (UnknownHostException e) {
            log.error("获得服务器IP地址失败，请检查:{}",e.getMessage());
            throw new AssertionError("获得服务器IP地址失败");
        }
    }

    /**
     * 返回相对服务器路径
     * @author czj
     * @date 2020/9/23 22:45
     * @param [path]
     * @return java.lang.String
     */
    public String toServerUrl(String path){
        return serverUrl+path;
    }

}
