package pers.czj.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pers.czj.util.VideoUtils;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.net.ConnectException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * 创建在 2020/8/21 11:48
 * 哔哩哔哩爬虫工具类
 */
@Component
public class GetVideoDataUtils {

    private static final Logger log = LoggerFactory.getLogger(GetVideoDataUtils.class);

    private static final String DEFAULT_USER_AGENT="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36";

    private static final String ORIGIN = "https://www.bilibili.com";

    @Value("${video.dir-path}")
    private String dirPath;

    @Value("${crawler.video-suffix:video.msv}")
    private String videoSuffix;

    @Value("${crawler.audio-suffix:audio.msv}")
    private String audioSuffix;

    @Value("${crawler.cover-suffix:.jpg}")
    private String coverSuffix;

    @Value("${crawler.product-suffix:.mp4}")
    private String productSuffix;

    public  List<Map<String,String>> syncGetData() throws IOException, InterruptedException {

        //构建请求组件
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = createRequest();

        //开始请求排行榜
        Response response = okHttpClient.newCall(request).execute();
        ResponseBody body = response.body();
        String bodyStr = body.string();

        //开始解析
        List<Map<String,String>> maps = HtmlUtils.resolverTop(bodyStr);
        return maps;




    }


    public Map<String,String> syncDownload(String title,String baseUrl) throws InterruptedException {
        final  CountDownLatch latch =  new CountDownLatch(3);
        HtmlUtils.Resource resource = HttpUtils.findVideoBaseUrl(baseUrl);

        String videoPath = dirPath+title+videoSuffix;
        String audioPath = dirPath+title+audioSuffix;
        String productPath = dirPath+title+productSuffix;
        String coverPath = dirPath+title+coverSuffix;
        Map<String,String> map = new HashMap<String, String>(){
            {
                put(resource.getVideoUrl(),videoPath);
                put(resource.getAudioUrl(),audioPath);
                put(resource.getCoverUrl(),coverPath);
            }
        };
        //开始下载
        map.forEach((key,value)->{
            HttpUtils.download(key, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    log.error("下载{}失败",key);
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    download(response.body().byteStream(),value);
                    latch.countDown();
                }
            });
        });
        latch.await();
        log.info("【{}】资源下载完毕",productPath);
        //开始合并
        VideoUtils.mergeResource(videoPath,audioPath,productPath);
        return new HashMap<String, String>(){
            {
                put("productUrl",productPath);
                put("coverUrl",coverPath);
            }
        };
    }
    private  Request createRequest(){
        return new Request.Builder()
                .url("https://www.bilibili.com/ranking?spm_id_from=333.851.b_7072696d61727950616765546162.3")
                .get()
                .addHeader("Connection","keep-alive")
                .addHeader("User-Agent",DEFAULT_USER_AGENT)
                .addHeader("Accept","*/*")
                .addHeader("Origin",ORIGIN)
                .addHeader("Sec-Fetch-Site","cross-site")
                .addHeader("Sec-Fetch-Mode","cors")
                .addHeader("Sec-Fetch-Dest","empty")
                .addHeader("Referer",ORIGIN)
                .addHeader("Accept-Encoding","identity")
                .addHeader("Accept-Language","zh-CN,zh;q=0.9")
                .build();
    }

    private  void download(InputStream inputStream, String path) throws IOException {
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path));
        byte[] bytes = new byte[1024];
        int len = -1;
        double total = 0;
        while((len=inputStream.read(bytes))!=-1){
            outputStream.write(bytes,0,len);
            total+=len;
        }
        outputStream.flush();
        outputStream.close();
        System.out.println(path+"下载完毕");
    }
}
