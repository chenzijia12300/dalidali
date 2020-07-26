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
            process.waitFor();
            log.debug("{}视频处理中",filePath+videoName);
            //获得视频基本信息
            String str = handlerInputStream(process.getInputStream());
            JSONObject jsonObject = JSONObject.parseObject(str);
            VideoBasicInfo basicInfo = handlerJson(jsonObject);
            //获得视频第一帧当封面
            String imageName = videoName.substring(0,videoName.lastIndexOf(".")+1)+"jpg";
            log.info("imageName:{}",imageName);
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

    private static String handlerInputStream(InputStream inputStream){
        try(
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))
        ){
            StringBuilder stringBuilder = new StringBuilder();
            int len = 0;
            String str = null;
            while((str=bufferedReader.readLine())!=null){
                stringBuilder.append(str);
            }
            return stringBuilder.toString();
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
            process.waitFor();
            log.info("生成封面完毕~");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author czj
     * 创建不同分辨率的视频
     * @date 2020/7/21 15:08
     * @param [fileName, destFileName, width, height]
     * @return void
     */
    public static void createOtherResolutionVideo(String fileName,String destFileName,String width,String height){
        List<String> list = new ArrayList<>();
        list.add("ffmpeg");
        list.add("-i");
        list.add(fileName);
        list.add("-vf");
        list.add("scale="+height+":"+width);
        list.add(destFileName);
        list.add("-hide_banner");
        ProcessBuilder builder = new ProcessBuilder();
        try {
            builder.command(list);
            builder.redirectErrorStream(true);
            log.info("尝试创建{}*{}分辨率的视频",height,width);
            Process process = builder.start();
            InputStream inputStream = process.getInputStream();
            String str = handlerInputStream(inputStream);
            log.info("str:{}",str);
            process.waitFor();
        }catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
