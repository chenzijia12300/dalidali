package pers.czj.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建在 2020/12/2 20:21
 */
@Service
public class VideoInfoCrawlerServiceImpl implements VideoInfoCrawlerService {

    private static final Logger log = LoggerFactory.getLogger(VideoInfoCrawlerServiceImpl.class);

    //待改
    public static final String GET_VIDEO_INFO_URL = "http://api.bilibili.com/x/web-interface/view";


    private static final String GET_CHANNEL_RECOMMEND_INFO = "https://app.bilibili.com/x/v2/region/dynamic";


    private String userCookie = "LIVE_BUVID=AUTO3115737980440898; buvid3=46BB9D0C-CD4B-49D0-9256-D87D53D14E60190963infoc; stardustvideo=1; laboratory=1-1; rpdid=|(kRRmll)l~0J'ul~J)|RuY); im_notify_type_269298390=0; dy_spec_agreed=1; blackside_state=1; CURRENT_FNVAL=80; fts=1552060614; gr_user_id=90fe7979-c7eb-4627-a400-0e7deb6a222d; grwng_uid=cc337b2f-d1ad-4413-84a4-2cf30344b45b; DedeUserID=269298390; DedeUserID__ckMd5=f1ce35d8e57462fe; _uuid=B318EA39-B161-C8E7-ADC7-09D5ABC7F35793619infoc; Hm_lvt_8a6e55dbd2870f0f5bc9194cddf32a02=1604288678,1605887050; CURRENT_QUALITY=80; SESSDATA=0ad5c765%2C1622258967%2C62040*b1; bili_jct=29afdb382b755b095e4d53b1ebb775fe; finger=1571944565; bp_video_offset_269298390=464895811405679146; sid=9arhywha; PVID=2; bp_t_offset_269298390=464954291675515158";

    private UserFeignClient feignClient;


    @Autowired
    public VideoInfoCrawlerServiceImpl(UserFeignClient feignClient) {
        this.feignClient = feignClient;
    }

    @Override
    public Map<String, String> getVideoBasicInfo(String url) {

        String paramStr = StrUtil.subAfter(url,"/",true);
        String requestUri;
        String keyStr = StrUtil.subWithLength(paramStr,0,2);
        String valueStr = StrUtil.subSuf(paramStr,2);
        log.info("url:{},keyStr:{},valueStr:{}",url,keyStr,valueStr);
        switch (keyStr.toLowerCase()){
            case "av":
                requestUri=StrUtil.concat(false,GET_VIDEO_INFO_URL,"?aid=",valueStr);
                break;
            case "bv":
                requestUri=StrUtil.concat(false,GET_VIDEO_INFO_URL,"?bvid=",valueStr);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + keyStr.toLowerCase());
        }

        Map<String,String> map = new HashMap<>();
        String bodyStr = HttpUtils.syncGetStr(requestUri, MapUtil.of("cookie","bfe_id=61a513175dc1ae8854a560f6b82b37af"),null);
        log.info("bodyStr:{}",bodyStr);
        JSONObject jsonObject = JSONObject.parseObject(bodyStr);
        JSONObject dataObject = JSONUtils.get(jsonObject,"data");
        JSONObject ownerObject = JSONUtils.get(dataObject,"owner");
        map.put("desc",dataObject.getString("desc"));
        map.put("categoryName",dataObject.getString("tname"));
        map.put("username",ownerObject.getString("name"));
        map.put("img",ownerObject.getString("face"));
        map.put("userId", String.valueOf(feignClient.createUserIfNeeded(map)));
        map.put("cid", String.valueOf(dataObject.getIntValue("cid")));
        return map;
    }


    public List<Map<String,String>> getChannelRecommendVideo(int rid){
        Map<String, String> requestMap = createDefaultRecommendRequestMap(rid);
        String bodyStr = HttpUtils.syncGetStr(GET_CHANNEL_RECOMMEND_INFO,null,requestMap);
        log.info("bodyStr:{}",bodyStr);
        JSONObject dataObject = JSONUtils.get(bodyStr,"data");
        JSONArray recommendArr = JSONUtils.getArr(dataObject,"recommend");
        List<Map<String,String>> list = new ArrayList<>();
        for (int i = 0; i < recommendArr.size(); i++) {
            list.add(getVideoInfo(recommendArr.getJSONObject(i)));
        }
        return list;
    }





    private Map<String,String> createDefaultRecommendRequestMap(int rid){
        Map<String,String> map = new HashMap<>();
        map.put("ad_extra","DF7BDA7977D753D9D895DB7F4DC5DCA6DF451585DA4555E1FD11A33E30187CB09D14731C342B005A16B266000113D98D6DB29BE4651A1E0FB2966233023B7287A9A90CCCF1EDDF6BE095824C3DFE34BB72CA764E67EB1855986B15D8A3A314F20ACE7782BE35FE31944D6748A12C88A5D5C0B2896F44F28FE4E917B11AB0EB2BA5CA385241C23C1830EA68A8829ADD38F803463F8A4BDACE91BCAB12AD1C21E373DA748B6AA6C782AB3BC0BED13C5FA81EB1361CFAD261465465E765A393D3F8FAE48D658C4296FA7BC59F821B683BCD3E1E9ADE466AF400E4F55FC51855734A3793BEA684C903543B6E6D95ADD99120B1F9264EEC2B879F92C137A2E21414E7BCA59F5B7B0A1051BE47C2D8C3A61D7BA7E6C2E506D36A97B429F460EC1386500AC551277075785646DBEE3C9FF0AD5B480B619B9E20D35C2887F17B2913984389C6335A8237E65DBE1530AFE99898F2A5919635BECDA29B729BE8FC5E99D3E87AE30AF5FBA6CE437440125E63631156E3563DAFF6F0577152DB325AD458434187BC3A0799DBEF1B671AD8B86439A89CED898DFFE44D5C9EE0CCDABFF72DABBB5155024662221A5A16206B8F72FCFF87A7B32ABA703CC683043E7A8101F3C845932A06381106AEB0155F45ECE7FBDE64C30ED523586E737BEF50117F7B872840DD7B3F5908F73036B40F4F8FF87CDB3847027165FA1A69705EB835E5E70EF3ADA7AC56691067D391CC1D1B3623A39308FFC7254F94AC1D036F71B921A2D52AD4D64615996EFE9EDF2E2293EF1854C8C1E41EA66366BA3F7588702000AAC4ACC6");
        map.put("appkey","1d8b6e7d45233436");
        map.put("build","6140500");
        map.put("c_locale","zh-Hans_CN");
        map.put("channel","bili");
        map.put("rid", String.valueOf(rid));
        return map;
    }

    private Map<String,String> getVideoInfo(JSONObject jsonObject){
        Map<String,String> map = new HashMap<>();
        map.put("title",jsonObject.getString("title"));
        String param = jsonObject.getString("goto")+jsonObject.getString("param");
        map.put("videoUrl","https://www.bilibili.com/video/"+param);
        return map;
    }


//    public static void main(String[] args) {
//        VideoInfoCrawlerService videoInfoCrawlerService = new VideoInfoCrawlerServiceImpl(null);
//        System.out.println(videoInfoCrawlerService.getVideoBasicInfo("https://www.bilibili.com/video/BV1Dy4y167tf"));
//    }
}
