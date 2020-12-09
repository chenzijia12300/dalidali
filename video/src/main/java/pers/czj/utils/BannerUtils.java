package pers.czj.utils;

import cn.hutool.core.lang.Pair;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建在 2020/12/3 20:30
 */
@Component
public class BannerUtils {

    public String bannerRequestUri = "https://app.bilibili.com/x/v2/feed/index?ad_extra=DF7BDA7977D753D9D895DB7F4DC5DCA6DF451585DA4555E1FD11A33E30187CB09D14731C342B005A16B266000113D98D6DB29BE4651A1E0FB2966233023B7287A9A90CCCF1EDDF6BE095824C3DFE34BB72CA764E67EB1855986B15D8A3A314F20ACE7782BE35FE31944D6748A12C88A5D5C0B2896F44F28FE4E917B11AB0EB2BA5CA385241C23C1830EA68A8829ADD38F803463F8A4BDACE91BCAB12AD1C21E373DA748B6AA6C782AB3BC0BED13C5FA81EB1361CFAD261465465E765A393D3F8FAE48D658C4296FA7BC59F821B683BCD3E1E9ADE466AF400E4F55FC51855734A3793BEA684C903543B6E6D95ADD99120B1F9264EEC2B879F92C137A2E21414E7BCA59F5B7B0A1051BE47C2D8C3A61D7BA7E6C2E506D36A97B429F460EC1386500AC551277075785646DBEE3C9FF0AD5B480B619B9E20D35C2887F17B2913984389C6335A8237E65DBE1530AFE99898F2A5919635BECDA29B729BE8FC5E99D3E87AE30AF5FBA6CE437440125E63631156E3563DAFF6F0577152DB325AD458434187BC3A0799DBEF1B671AD8B86439A89CED898DFFE44D5C9EE0CCDABFF72DABBB5155024662221A5A16206B8F72FCFF87A7B32ABA703CC683043E7A8101F3C845932A06381106AEB0155F45ECE7FBDE64C30ED523586E737BEF50117F7B872840DD7B3F5908F73036B40F4F8FF87CDB3847027165FA1A69705EB835E5E70EF3ADA7AC56691067D391CC1D1B3623A39308FFC7254F94AC1D036F71B921A2D52AD4D64615996EFE9EDF2E2293EF1854C8C1E41EA66366BA3F7588702000AAC4ACC6&appkey=1d8b6e7d45233436&autoplay_card=11&banner_hash=17503758581590021057&build=6140500&c_locale=zh-Hans_CN&channel=bili&column=2&device_name=Android%20SDK%20built%20for%20x86&device_type=0&flush=6&fnval=16&fnver=0&force_host=0&fourk=0&guidance=0&https_url_req=0&idx=1606722735&inline_danmu=2&inline_sound=1&login_event=0&mobi_app=android&network=wifi&open_event=&platform=android&pull=true&qn=32&recsys_mode=0&s_locale=zh-Hans_CN&splash_id=&statistics={%22appId%22%3A1%2C%22platform%22%3A3%2C%22version%22%3A%226.14.0%22%2C%22abtest%22%3A%22%22}&ts=1606724052&sign=263eb160a9dc82676829679d0c82ecf5";

    public List<Map<?,?>> getWebBanner(){
        List<Map<?,?>> list = new ArrayList<>();
        String bodyStr = HttpUtils.syncGetStr(bannerRequestUri,null,null);
        JSONObject jsonObject = JSONUtils.get(bodyStr,"data").getJSONArray("items").getJSONObject(0);
        JSONArray banners = JSONUtils.getArr(jsonObject,"banner_item");

        banners.toJavaList(JSONObject.class)
                .forEach(item->{
                    list.add(
                            new HashMap<String,String>(){
                                {
                                    put("title",item.getString("title"));
                                    put("image",item.getString("image"));
                                }
                            }
                    );
                });
        return list;
    }
}
