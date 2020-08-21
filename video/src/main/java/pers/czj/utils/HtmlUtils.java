package pers.czj.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建在 2020/5/17 22:11
 * 解析html
 */
public class HtmlUtils {
    //你问我为什么下标是2
    //更改于2020/8/20 下标为5啦
    private static final int DEFAULT_RESOURCE_INDEX=4;

    private static final int DEFAULT_INFO_INDEX=3;


    /**
     * @author czj
     * 获得视频url和音频url
     * @date 2020/8/21 11:55
     * @param [element]
     * @return pers.czj.utils.HtmlUtils.Resource
     */
    public static Resource resolver(String html){
        Document document = Jsoup.parse(html);
        Elements scriptElements = document.getElementsByTag("script");
        Element resourceElement = scriptElements.get(DEFAULT_RESOURCE_INDEX);
//        Element infoElement = scriptElements.get(DEFAULT_INFO_INDEX);
        Resource resource = resolverResource(resourceElement);
        return resource;
    }


    private static Resource resolverResource(Element element){
        String text = element.toString();
        System.out.println(text);
        text = text.substring(text.indexOf("{"),text.lastIndexOf("}")+1);
        //获取baseUrl
        JSONObject jsonObject = JSONObject.parseObject(text);
        JSONObject  dashObject = jsonObject.getJSONObject("data").getJSONObject("dash");
        JSONArray videoArray = dashObject.getJSONArray("video").getJSONObject(0).getJSONArray("backupUrl");
        JSONArray audioArray = dashObject.getJSONArray("audio").getJSONObject(0).getJSONArray("backupUrl");
        return new Resource(videoArray.getString(0),audioArray.getString(0));
    }

    public static List<Map<String,String>> resolverTop(String bodyStr){
        Document document = Jsoup.parse(bodyStr);
        List<Element> elementList = document.select(".rank-list").get(0).getElementsByTag("li");
        List<Map<String,String>> maps = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        for (Element element:elementList){
            Element infoElement = element.getElementsByClass("title").get(0);
            map.put("title",infoElement.text());
            map.put("videoUrl",infoElement.attr("href"));
            maps.add(map);
            map = new HashMap<>();
        }
        return maps;
    }

/*    private static String resolverInfo(Element element){
        String text = element.toString();
        text = text.substring(text.indexOf("{"),text.lastIndexOf("}"));
        text = text.substring(0,text.lastIndexOf("}")+1);
        System.out.println(text);
        JSONObject jsonObject = JSONObject.parseObject(text);
        JSONObject videoData = jsonObject.getJSONObject("videoData");
        return videoData.getString("title");
    }*/


    @Data
    public static class Resource{

        private String videoUrl;

        private String audioUrl;


        public Resource(String videoUrl, String audioUrl) {
            this.videoUrl = videoUrl;
            this.audioUrl = audioUrl;
        }
    }
}
