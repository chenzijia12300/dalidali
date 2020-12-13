package pers.czj.util;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.omg.PortableServer.POA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.czj.common.VideoBasicInfo;
import pers.czj.constant.VideoCoverTypeEnum;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 创建在 2020/7/12 13:54
 * 视频处理工具类
 */
public class VideoUtils {

    private static final Logger log = LoggerFactory.getLogger(VideoUtils.class);

    private static final String VIDEO_IMAGE_SUFFIX="%d.png";

    private static final String MERGE_IMAGE_SUFFIX="_preview.png";

    public static VideoBasicInfo getVideoInfo(String filePath, String videoName, VideoCoverTypeEnum type){
        long startTime = System.currentTimeMillis();
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
            Process process = processBuilder.start();
            log.debug("{}视频处理中",filePath+videoName);
            //获得视频基本信息
            String str = handlerInputStream(process.getInputStream());
            process.waitFor();
            JSONObject jsonObject = JSONObject.parseObject(str);
            VideoBasicInfo basicInfo = handlerJson(jsonObject);


            String coverName = null;
            switch (type){
                case GIF:
                    coverName = videoName.substring(0, videoName.lastIndexOf(".") + 1) + "gif";
                    log.info("coverName:{}", coverName);
                    createGif(filePath+videoName,filePath+coverName,basicInfo.getWidth()<basicInfo.getHeight());
                    basicInfo.setCover(filePath+coverName);
                    basicInfo.setCompressCover(filePath+coverName);
                    break;
                case EMPTY:
                    coverName = videoName.substring(0, videoName.lastIndexOf(".") + 1) + "jpg";
                    log.info("coverName:{}", coverName);
                    createFirstImage(filePath + videoName, filePath + coverName);
                    basicInfo.setCompressCover(ImageUtils.compress(filePath+coverName).getAbsolutePath());
                    basicInfo.setCover(filePath + coverName);
                default:
                    break;
            }
            return basicInfo;
        } catch (IOException e) {
            log.error("处理视频信息出现错误：{}",e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            log.info("处理并返回所需视频信息共耗费{}秒",(System.currentTimeMillis()-startTime)/1000.0);
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
        Integer widthInt = streamObject.getInteger("width");
        Integer heightInt = streamObject.getInteger("height");


/*        //处理小于最小值的情况
        String width = widthInt<640?"640":String.valueOf(widthInt);
        String height = heightInt<360?"360":String.valueOf(heightInt);*/


        VideoBasicInfo videoBasicInfo = new VideoBasicInfo();
        videoBasicInfo.setDuration((long) Double.parseDouble(duration));
        videoBasicInfo.setWidth(widthInt);
        videoBasicInfo.setHeight(heightInt);
        return videoBasicInfo;
    }


    private static String handlerInputStream(InputStream inputStream){
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))){
            StringBuilder stringBuilder = new StringBuilder();
            String str = null;
            while((str=bufferedReader.readLine())!=null){
                stringBuilder.append(str);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            log.info("读取IO流出现异常:{}",e);
        }
        return "";
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


    public static void createGif(String filePath,String gifPath,boolean isPortrait){
        List<String> list = ListUtil.of("ffmpeg","-ss","00:00:05","-t","3","-i",filePath,"-r","15","-s",isPortrait?"116*206":"206*116",gifPath);
        ProcessBuilder builder = new ProcessBuilder(list);
        try {
            Process process = builder.start();
            process.waitFor();
            log.info("生成视频封面动图完毕~");
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
        list.add("scale="+width+":"+height);
        list.add(destFileName);
        list.add("-hide_banner");
        ProcessBuilder builder = new ProcessBuilder();
        try {
            builder.command(list);
            log.info("尝试创建{}*{}分辨率的视频",width,height);
            Process process = builder.start();
            log.info("创建{}*{}分辨率的视频成功！",width,height);
            process.waitFor();
        }catch (IOException e){
            log.info("创建{}*{}分辨率的视频失败，出现IO异常",width,height);
        } catch (InterruptedException e) {
            log.info("中断生成失败操作");
            FileUtil.del(destFileName);
        }
    }

    /**
     * @author czj
     * 创建预览图（雪碧图）
     * @date 2020/7/27 21:08
     * @param [videoPath, videoName]
     * @return string
     */
    public static String createPreviewImage(String videoPath,String videoName,double second){
        log.debug("videoPath:{}\nvideoName:{}\nsecond:{}",videoPath,videoName,second);

        /*
            每second秒生成一帧图片
         */
        int ch = videoName.lastIndexOf(".");
        String coverName = videoName.substring(0,ch);
        log.debug("coverName:{}",coverName);
        List<String> list = new ArrayList<>();
        list.add("ffmpeg");
        list.add("-i");
        list.add(videoPath+videoName);
        list.add("-vf");
        list.add("fps="+second);
        list.add(videoPath+coverName+VIDEO_IMAGE_SUFFIX);
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(list);
        try {
            Process process = processBuilder.start();
            process.waitFor();
            /*
                合并图片,并删除本地图片
             */
            list.clear();
            list.add("montage");
            list.add(videoPath+coverName+"*.png");
            list.add("-tile");
            list.add("10");
            list.add("-geometry");

            //设置预览图的大小，后面需要修改。
            list.add("206x116");
            list.add(videoPath+coverName+MERGE_IMAGE_SUFFIX);
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(list);
            Process process1 = builder.start();
            process1.waitFor();
            return coverName+MERGE_IMAGE_SUFFIX;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将通过爬虫下载获得的 视频资源，音频资源进行合并
     * @author czj
     * @date 2020/8/21 11:30
     * @param [videoPath, audioPath, outputPath, countDownLatch]
     * @return void
     */
    public static void mergeResource(String videoPath, String audioPath, String outputPath){

        if (!FileUtil.exist(videoPath)||!FileUtil.exist(audioPath)){
            log.info("合并文件不存在");
            return;
        }


        merge(videoPath,audioPath,outputPath);
        boolean videoFlag = FileUtil.del(videoPath);
        boolean audioFlag = FileUtil.del(audioPath);
        log.info("删除视频:{}",videoFlag);
        log.info("删除音频:{}",audioFlag);
        log.info("合并成功:{} + {}",videoPath,audioPath);


        //没必要使用new一个线程然后wait它
/*        CountDownLatch mergeLatch = new CountDownLatch(1);
        new Thread(()->{
            merge(videoPath,audioPath,outputPath);
            boolean videoFlag = FileUtil.del(videoPath);
            boolean audioFlag = FileUtil.del(audioPath);
            log.info("删除视频:{}",videoFlag);
            log.info("删除音频:{}",audioFlag);
            log.info("合并成功:{} + {}",videoPath,audioPath);
            mergeLatch.countDown();
        }).start();
        try {
            mergeLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * 将视频和音频合并，都需绝对路径
     * @author czj
     * @date 2020/10/4 0:05
     * @param [videoPath, audioPath, outputPath]
     * @return void
     */
    public static void merge(String videoPath, String audioPath,String outputPath) {
        List<String> cutpic = new ArrayList<String>();
        cutpic.add("ffmpeg");
        cutpic.add("-i");
        cutpic.add(videoPath);
        cutpic.add("-i");
        cutpic.add(audioPath);
        cutpic.add("-codec");
        cutpic.add("copy");
        cutpic.add(outputPath);
        ProcessBuilder builder = new ProcessBuilder();
        try {

            builder.command(cutpic);
            // 如果此属性为 true，则任何由通过此对象的 start() 方法启动的后续子进程生成的错误输出都将与标准输出合并，
            // 因此两者均可使用 Process.getInputStream() 方法读取。这使得关联错误消息和相应的输出变得更容易
            Process process = builder.start();
            log.info("视频:[{}]---音频:[{}]开始合并",videoPath,audioPath);
            process.waitFor();
            log.info("生成[{}]成品视频",outputPath);
        } catch (Exception e) {
            log.error("{}合并出错，重新尝试",outputPath);
        }
    }


    public static class DealProcessStream implements Runnable{

        @Override
        public void run() {

        }
    }


}
