package pers.czj.util;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.omg.PortableServer.POA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 创建在 2020/7/12 13:54
 * 视频处理工具类
 */
public class VideoUtils {



    private static final Logger log = LoggerFactory.getLogger(VideoUtils.class);

    public static Map<String,String> getVideoInfo(String filePath){
        List<String> commandList = new ArrayList<>();
        commandList.add("ffprobe");
        commandList.add("-select_streams");
        commandList.add("v");
        commandList.add("-show_entries");
        commandList.add("format=duration");
        commandList.add("-show_streams");
        commandList.add("-v");
        commandList.add("quiet");
        commandList.add("-of");
        commandList.add("csv='p=0'");
        commandList.add("-of");
        commandList.add("json");
        commandList.add("-i");
        commandList.add(filePath);
        ProcessBuilder processBuilder = new ProcessBuilder();
        try {
            processBuilder.command(commandList);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            log.debug("{}视频处理中",filePath);
            process.waitFor();
            Map<String,String> map = handlerInputStream(process.getInputStream());
            map.forEach((key,value)->{
                log.debug("key:{},value:{}",key,value);
            });
        } catch (IOException e) {
            log.error("处理视频信息出现错误：{}",e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Map<String,String> handlerInputStream(InputStream inputStream){
        try(
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))
        ){
            StringBuilder stringBuilder = new StringBuilder();
            int len = 0;
            String str = null;
            while((str=bufferedReader.readLine())!=null){
                stringBuilder.append(str);
            }
            JSONObject jsonObject = JSONObject.parseObject(stringBuilder.toString());
            return handlerJson(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Map<String,String> handlerJson(JSONObject jsonObject){
        //获得视频时长
        String duration = jsonObject.getJSONObject("format").getString("duration");
        JSONObject streamObject = jsonObject.getJSONArray("streams").getJSONObject(0);
        String width = streamObject.getString("width");
        String height = streamObject.getString("height");
        Map<String,String> map = new HashMap<>();
        map.put("duration",duration);
        map.put("width",width);
        map.put("height",height);
        return map;
    }

    public static void main(String[] args) {
        getVideoInfo("C:\\Users\\ZJ\\Videos\\Captures\\video.mp4 ");
/*        Pattern pattern = Pattern.compile("\\d*");
        String str = "1234ABCD56789ABCD";
        Matcher matcher = pattern.matcher(str);
        System.out.printlsn(matcher.find());
        System.out.println(matcher.group());*/
    }

/*    public static Map<String,String> getVideoInfo(InputStream inputStream) {

    }*/
}
