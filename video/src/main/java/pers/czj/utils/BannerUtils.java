package pers.czj.utils;

import cn.hutool.core.lang.Pair;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
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

    public static final String DEFAULT_BANNER_ITEM = "[\n" +
            "                    {\n" +
            "                        \"id\": 620447,\n" +
            "                        \"title\": \"前方高能！导师们真的很敢说\",\n" +
            "                        \"image\": \"http://i0.hdslb.com/bfs/archive/0924b5ad59ff90595f4e54c4428044aa35bf2e75.jpg\",\n" +
            "                        \"hash\": \"6cbaf68ab77c5a8b89921ac2fb1de292\",\n" +
            "                        \"uri\": \"https://www.bilibili.com/bangumi/play/ep370203\",\n" +
            "                        \"request_id\": \"1607431260367q172a21a137a16q4929\",\n" +
            "                        \"src_id\": 3151,\n" +
            "                        \"is_ad_loc\": true,\n" +
            "                        \"client_ip\": \"27.38.254.63\",\n" +
            "                        \"server_type\": 0,\n" +
            "                        \"resource_id\": 3150,\n" +
            "                        \"index\": 1,\n" +
            "                        \"cm_mark\": 0\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": 0,\n" +
            "                        \"title\": \"12月10日正式发售\",\n" +
            "                        \"image\": \"https://i0.hdslb.com/bfs/sycp/creative_img/202012/a914e3960541fcf7282de63b30f3826d.jpg\",\n" +
            "                        \"hash\": \"da534786c35c7a2a5406b8efc55130a1\",\n" +
            "                        \"uri\": \"https://game.bilibili.com/cyberpunk2077/h5?sourceFrom=777&msource=1&source=trackadf_ad9ad4ce9e894de3846c48186dfcff06&gameID=4079\",\n" +
            "                        \"request_id\": \"1607431260367q172a21a137a16q4929\",\n" +
            "                        \"creative_id\": 438761253224370176,\n" +
            "                        \"src_id\": 3152,\n" +
            "                        \"is_ad_loc\": true,\n" +
            "                        \"ad_cb\": \"CAAQABiAoJP9j+GyiwYgACgAMCQ40BhCIDE2MDc0MzEyNjAzNjdxMTcyYTIxYTEzN2ExNnE0OTI5SM+R+pLkLlIG5rex5ZyzWgblub/kuJxiBuS4reWbvWhkcAF4gICAgKAVgAEAiAG5eJIBDDI3LjM4LjI1NC42M5oB+wphbGw6Y3BjX2Nyb3dkX3RhcmdldCxjcGNUYWdGaWx0ZXI6dW5kZWZpbmVkLHBwY3RyOmNvbnN0YW50LG5vX2FkOnVuZGVmaW5lZCxicnVzaF9kdXA6ZGVmYXVsdCxmZF9wY3RyOmNvbnN0YW50LGN2cl9wOm5uX3YxLGRjdnJmOmNsdWVfY29zdF91cCxjdnJfZjpjb25zdGFudCxjcGE6cGMsVVY6dW5kZWZpbmVkLGhhc2hVVjp1bmRlZmluZWQscHY6QSxmZF9yYW5kb206ZGVmYXVsdCxmbG93X3JhdGlvOnIzLGRtcF9hZ2U6YmllX3YyLG5GZWRCYWNrOnN0cmljdCxxaWZlaTpiYXNlLGZpbHRlck9wdDpvcGVuLGlubmVyX2VuaGFuY2U6Y2xvc2UsUlM6Y29uc3RhbnQsZHBzOmRlZmF1bHQsb3BlbmZseW5ldzpjb25zdGFudCxmbHlfcGN2cjpwcF9keW4sZmx5X29jcG06YWExLGZseV9jcGE6cGlkcCxoNV92OmNvbXBhcmUscEVOOlBSLHNGQnJ1OjIsZmRTQjpkZWZhdWx0LHRpbWVGcmVxOmRlZmF1bHQsY3VjbzoxMjAscHVjbzpkeW5hbWljLHV2VGVzdDpiMixvY3BtVGhyOmRlZmF1bHQsZG93bmdyYWRlOm5vX3YyLGRBZHg6ZGVmYXVsdCx1c2VHcnBjOnRydWUsbWVyY3lQYjphbGwsZnJlUzpjb25zdGFudCxjb2Fyc2U6Y29hcnNlX2N2cl9kaXJlY3Rjcm9zcyxwcEI6c2hvdyxjcm93ZFM6Qyxjb2xkUjpjb25zdGFudCxmbHlfdHJ1bnA6bGVmdCxzbWFydGJpZDpkZWZhdWx0LGFsbEZsb3c6ZGVmYXVsdCxjcm93ZHNMb2c6ZW5hYmxlLGVjcG06ZGVmYXVsdCxwY3RyX2NwbTpjcG0scGN0cl9jb25zdGFudDpkZnQsZHluYW1pY19mbG93X2NvbnRyb2w6c3BsaXQgdGhlIGZsb3cgYnkgbWlkLG91dGVyQmVhdElubmVyOmRlZmF1bHQsb3V0ZXJRdWl0OmRlZmF1bHQsY29sZF9ib290X2V4cDpkZWZhdWx0LG50aF9icnVzaF9ldmVudDpkZWZhdWx0LGxvd19xdHlfY3JlYXRpdmU6bG93X3F0eSxlbmFibGVQYXJhbGxlbDp0cnVlLGxpbWl0X2FkeDpkZWZhdWx0LHNvcnRBZHg6c29ydEFkeF90cnVlLG9jcG1Ud29TdGFnZVBjdnJUaHJlc2hvbGRFeHA6ZGVmYXVsdCxwcFNCOm9wZW4sY3JlYXRpdmVGcmVxRmlsdGVyOmNsdXN0ZXIsYmxvY2tDb21tZW50OmRlZmF1bHQsb2NwbV9zdXBwb3J0Om9wZW5Db250ZW50VXBDb25maWdMYW1iZGEsZmx5X2NwbV9lY3BtOm9wZW4sbWV0OjMzNSxidWRnZXRfc21vb3RoX25ldzpjb25zdGFudCxwY0V4cGVyaW1lbnQ6cGNFeHBlcmltZW50RW5hYmxlLHBjcGN2cjpiYXNlLGVuYWJsZUFwcFN0b3JlUHJlbG9hZDplbmFibGUsY2Fzc2luaUV4cDpvcGVuLENyZWF0aXZlUXVhbGl0eTpmaXJzdCxydGFFeHA6Y2xvc2UsZ2lmX2V4cDpiYXNlLGlubGluZUV4cDpkZWZhdWx0LHByb2dyYW1DcmVhdGl2ZTpmZWVkY3RyLG9wZW5CdXNpbmVzc1VwQ3BjOmRlZmF1bHQsYnJhbmRJbmZvRXhwOmRlZmF1bHQsZHBhMlN0cmF0ZWd5OmRlZmF1bHQscGxhdGZvcm06ZGVmYXVsdKABAKgBALIBIE3xjgfFqCLI5pi7KRRcVttqpgYB1aTuEAODLcAWN/tFugGAAWh0dHBzOi8vZ2FtZS5iaWxpYmlsaS5jb20vY3liZXJwdW5rMjA3Ny9oNT9zb3VyY2VGcm9tPTc3NyZtc291cmNlPTEmc291cmNlPXRyYWNrYWRmX2FkOWFkNGNlOWU4OTRkZTM4NDZjNDgxODZkZmNmZjA2JmdhbWVJRD00MDc5wgEA0gEA2AGqAeABAOgBAPABAPgBAIACAIgCALgCAMACAMgCANACANgCAOoCAPACr58J+AIAiAMEkgMAqAMAsAMAuAMAwgMAyAOfjQbSA0F7IjEiOiI0Mzg3NjEyNTMyMjQzNzAxNzYiLCIyIjoiMzYiLCI0IjoiOTAiLCI1IjoiMjkyIiwiNiI6IjM2XzAifeADAOgDBfADAPoDBW90aGVyggQJbnVsbDpudWxsiAQAkAQAmAQAoAQAqgQECAAQBA==\",\n" +
            "                        \"click_url\": \"https://ad-bili-data.biligame.com/v2/api/click/mobile_bili?mid=__MID__&os=0&idfa=__IDFA__&buvid=__BUVID__&android_id=__ANDROIDID__&imei=__IMEI__&mac=__MAC__&duid=__DUID__&ip=27.38.254.63&request_id=1607431260367q172a21a137a16q4929&ts=__TS__&ua=__UA__&ad_channel_id=4&ad_f=ad9ad4ce9e894de3846c48186dfcff06&game_id=4079\",\n" +
            "                        \"client_ip\": \"27.38.254.63\",\n" +
            "                        \"server_type\": 1,\n" +
            "                        \"resource_id\": 3150,\n" +
            "                        \"index\": 2,\n" +
            "                        \"cm_mark\": 2,\n" +
            "                        \"extra\": {\n" +
            "                            \"use_ad_web_v2\": false,\n" +
            "                            \"show_urls\": [],\n" +
            "                            \"click_urls\": [\n" +
            "                                \"https://ad-bili-data.biligame.com/v2/api/click/mobile_bili?mid=__MID__&os=0&idfa=__IDFA__&buvid=__BUVID__&android_id=__ANDROIDID__&imei=__IMEI__&mac=__MAC__&duid=__DUID__&ip=27.38.254.63&request_id=1607431260367q172a21a137a16q4929&ts=__TS__&ua=__UA__&ad_channel_id=4&ad_f=ad9ad4ce9e894de3846c48186dfcff06&game_id=4079\"\n" +
            "                            ],\n" +
            "                            \"download_whitelist\": [],\n" +
            "                            \"open_whitelist\": [],\n" +
            "                            \"card\": {\n" +
            "                                \"card_type\": 0,\n" +
            "                                \"title\": \"12月10日正式发售\",\n" +
            "                                \"covers\": [\n" +
            "                                    {\n" +
            "                                        \"url\": \"https://i0.hdslb.com/bfs/sycp/creative_img/202012/a914e3960541fcf7282de63b30f3826d.jpg\",\n" +
            "                                        \"loop\": 0,\n" +
            "                                        \"image_height\": 0,\n" +
            "                                        \"image_width\": 0,\n" +
            "                                        \"gif_tag_show\": false\n" +
            "                                    }\n" +
            "                                ],\n" +
            "                                \"jump_url\": \"https://game.bilibili.com/cyberpunk2077/h5?sourceFrom=777&msource=1&source=trackadf_ad9ad4ce9e894de3846c48186dfcff06&gameID=4079\",\n" +
            "                                \"desc\": \"\",\n" +
            "                                \"callup_url\": \"\",\n" +
            "                                \"long_desc\": \"\",\n" +
            "                                \"ad_tag\": \"\",\n" +
            "                                \"extra_desc\": \"\",\n" +
            "                                \"universal_app\": \"\",\n" +
            "                                \"duration\": \"\",\n" +
            "                                \"adver\": {\n" +
            "                                    \"adver_id\": 36,\n" +
            "                                    \"adver_type\": 3,\n" +
            "                                    \"adver_desc\": \"推荐了\"\n" +
            "                                },\n" +
            "                                \"ad_tag_style\": {\n" +
            "                                    \"type\": 2,\n" +
            "                                    \"text\": \"广告\",\n" +
            "                                    \"text_color\": \"#999999FF\",\n" +
            "                                    \"bg_border_color\": \"#999999FF\",\n" +
            "                                    \"bg_color\": \"\",\n" +
            "                                    \"text_color_night\": \"#686868\",\n" +
            "                                    \"border_color\": \"#999999FF\",\n" +
            "                                    \"bg_color_night\": \"\",\n" +
            "                                    \"border_color_night\": \"#686868\"\n" +
            "                                },\n" +
            "                                \"feedback_panel\": {\n" +
            "                                    \"panel_type_text\": \"广告\",\n" +
            "                                    \"feedback_panel_detail\": []\n" +
            "                                },\n" +
            "                                \"fold_time\": 0,\n" +
            "                                \"live_room_popularity\": 0,\n" +
            "                                \"live_tag_show\": false,\n" +
            "                                \"quality_infos\": [],\n" +
            "                                \"dynamic_text\": \"12月10日正式发售\"\n" +
            "                            },\n" +
            "                            \"report_time\": 2000,\n" +
            "                            \"sales_type\": 31,\n" +
            "                            \"special_industry\": false,\n" +
            "                            \"special_industry_tips\": \"\",\n" +
            "                            \"preload_landingpage\": 0,\n" +
            "                            \"upzone_entrance_type\": 0,\n" +
            "                            \"upzone_entrance_report_id\": 0,\n" +
            "                            \"click_area\": 0,\n" +
            "                            \"shop_id\": 0,\n" +
            "                            \"up_mid\": 0,\n" +
            "                            \"track_id\": \"8hG-WsbV0pbTQA1mBBLe7dsXmzvmfYCBfrMqhkCUc7VvGMhYLBrdNLL9bem2bB_6EyD9ht7TtT6krYYh3bigEDzCTamWX45x_FHxwuyry-o=\",\n" +
            "                            \"enable_store_direct_launch\": 0\n" +
            "                        }\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"id\": 620151,\n" +
            "                        \"title\": \"在B站，你甚至能看到沈腾出演《无间道》\",\n" +
            "                        \"image\": \"http://i0.hdslb.com/bfs/archive/1c2c8c3c8ebb8234202a30a59f8ecd4047ba755b.jpg\",\n" +
            "                        \"hash\": \"72b1388d7dbe695437c16d31ec8f7ce5\",\n" +
            "                        \"uri\": \"https://www.bilibili.com/read/cv8711314\",\n" +
            "                        \"request_id\": \"1607431260367q172a21a137a16q4929\",\n" +
            "                        \"src_id\": 3153,\n" +
            "                        \"is_ad_loc\": true,\n" +
            "                        \"client_ip\": \"27.38.254.63\",\n" +
            "                        \"server_type\": 0,\n" +
            "                        \"resource_id\": 3150,\n" +
            "                        \"index\": 3,\n" +
            "                        \"cm_mark\": 0\n" +
            "                    }\n" +
            "                ]";

    public List<Map<?,?>> getWebBanner(){
        List<Map<?,?>> list = new ArrayList<>();
        String bodyStr = HttpUtils.syncGetStr(bannerRequestUri,null,null);
        JSONObject jsonObject = JSONUtils.get(bodyStr,"data").getJSONArray("items").getJSONObject(0);
        JSONArray banners = null;
        if (jsonObject.containsKey("banner_item"))
             banners = JSONUtils.getArr(jsonObject,"banner_item");
        else
            banners = JSON.parseArray(DEFAULT_BANNER_ITEM);
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
