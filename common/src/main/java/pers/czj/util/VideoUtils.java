package pers.czj.util;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.omg.PortableServer.POA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.czj.common.VideoBasicInfo;

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

    public static VideoBasicInfo getVideoInfo(String filePath,String videoName){
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
        commandList.add(filePath+videoName);
        ProcessBuilder processBuilder = new ProcessBuilder();
        try {
            processBuilder.command(commandList);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            log.debug("{}视频处理中",filePath);
            process.waitFor();
            //获得视频基本信息
            VideoBasicInfo basicInfo = handlerInputStream(process.getInputStream());
            //获得视频第一帧当封面
            String imageName = videoName.substring(0,videoName.lastIndexOf("."));
            createFirstImage(filePath+videoName,filePath+imageName);
            basicInfo.setCover(filePath+imageName);
            return basicInfo;
        } catch (IOException e) {
            log.error("处理视频信息出现错误：{}",e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @author czj
     * 处理FFMPEG的JSON数据
     * @date 2020/7/17 21:57
     * @param [jsonObject]
     * @return VideoBasicInfo
     */
    private static VideoBasicInfo handlerJson(JSONObject jsonObject){
        //获得视频时长
        String duration = jsonObject.getJSONObject("format").getString("duration");
        JSONObject streamObject = jsonObject.getJSONArray("streams").getJSONObject(0);
        String width = streamObject.getString("width");
        String height = streamObject.getString("height");
        VideoBasicInfo videoBasicInfo = new VideoBasicInfo();
        videoBasicInfo.setDuration((long) Double.parseDouble(duration));
        videoBasicInfo.setWidth(width);
        videoBasicInfo.setHeight(height);
        return videoBasicInfo;
    }

    private static VideoBasicInfo handlerInputStream(InputStream inputStream){
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

    public static void createFirstImage(String filePath,String imagePath){
        List<String> list = new ArrayList<>();
        list.add("ffmpeg");
        list.add("-i");
        list.add(filePath);
        list.add("-y");
        list.add("-f");
        list.add("mjpeg");
        list.add("-ss");
        list.add("0.1");
        list.add("-t");
        list.add("0.001");
        list.add(imagePath);
        ProcessBuilder builder = new ProcessBuilder(list);
        try {
            Process process = builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
