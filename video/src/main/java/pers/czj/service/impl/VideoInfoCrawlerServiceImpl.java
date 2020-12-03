package pers.czj.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pers.czj.feign.UserFeignClient;
import pers.czj.service.VideoInfoCrawlerService;
import pers.czj.utils.HttpUtils;
import pers.czj.utils.JSONUtils;

import java.sql.Struct;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建在 2020/12/2 20:21
 */
@Service
public class VideoInfoCrawlerServiceImpl implements VideoInfoCrawlerService {

    private static final Logger log = LoggerFactory.getLogger(VideoInfoCrawlerServiceImpl.class);

    //待改
    private String requestUri = "http://api.bilibili.com/x/web-interface/view";

    @Autowired
    private UserFeignClient feignClient;


    @Override
    public Map<String, String> getVideoBasicInfo(String url) {

        String paramStr = StrUtil.subAfter(url,"/",true);

        String keyStr = StrUtil.subWithLength(paramStr,0,2);
        String valueStr = StrUtil.subSuf(paramStr,2);
        log.info("keyStr:{},valueStr:{}",keyStr,valueStr);
        switch (keyStr.toLowerCase()){
            case "av":
                requestUri=StrUtil.concat(false,requestUri,"?aid=",valueStr);
                break;
            case "bv":
                requestUri=StrUtil.concat(false,requestUri,"?bvid=",valueStr);
                break;
        }

        Map<String,String> map = new HashMap<>();
        String bodyStr = HttpUtils.syncGetStr(requestUri,null);
        JSONObject dataObject = JSONUtils.get(bodyStr,"data");
        JSONObject ownerObject = JSONUtils.get(dataObject,"owner");
        map.put("desc",dataObject.getString("desc"));
        map.put("categoryName",dataObject.getString("tname"));
        map.put("username",ownerObject.getString("name"));
        map.put("img",ownerObject.getString("face"));
        map.put("userId", String.valueOf(feignClient.createUserIfNeeded(map)));
        return map;
    }

    public static void main(String[] args) {
        VideoInfoCrawlerService videoInfoCrawlerService = new VideoInfoCrawlerServiceImpl();
        System.out.println(videoInfoCrawlerService.getVideoBasicInfo("https://www.bilibili.com/video/BV1D54y1z7br"));
    }
}
