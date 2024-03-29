package pers.czj.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pers.czj.util.VideoUtils;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * 创建在 2020/8/21 11:48
 * 哔哩哔哩爬虫工具类
 */
@Component
public class GetVideoDataUtils {

    private static final Logger log = LoggerFactory.getLogger(GetVideoDataUtils.class);

    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36";

    private static final String DEFAULT_TOP_URL = "https://www.bilibili.com/v/popular/rank/all?spm_id_from=333.851.b_7072696d61727950616765546162.3";

    private static final String ORIGIN = "https://www.bilibili.com";

    private ExecutorService executorService = Executors.newFixedThreadPool(3);

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

    public List<Map<String, String>> syncGetData(String url) {

        //构建请求组件
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = createRequest(StrUtil.isEmpty(url) ? DEFAULT_TOP_URL : url);

        //开始请求排行榜
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            log.error("解析排行榜信息出错Url:{}", request.url());
            e.printStackTrace();
        }
        ResponseBody body = response.body();
        String bodyStr = null;
        try {
            bodyStr = body.string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //开始解析
        List<Map<String, String>> maps = HtmlUtils.resolverTop(bodyStr);
        return maps;


    }


    public Map<String, String> syncDownload(String title, String baseUrl) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(3);
        HtmlUtils.Resource resource = HttpUtils.findVideoBaseUrl(baseUrl);
        String uuid = UUID.randomUUID().toString();
        log.info("resource:{}", resource);
        String videoPath = dirPath + uuid + videoSuffix;
        String audioPath = dirPath + uuid + audioSuffix;
        String productPath = dirPath + uuid + productSuffix;
        String coverPath = dirPath + uuid + coverSuffix;
        Map<String, String> map = new HashMap<String, String>() {
            {
                put(resource.getVideoUrl(), videoPath);
                put(resource.getAudioUrl(), audioPath);
                put(resource.getCoverUrl(), coverPath);
            }
        };
        //开始下载
        map.forEach((key, value) -> {
            HttpUtils.download(key, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    log.error("下载{}失败", key);
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    download(response.body().byteStream(), value);
                    latch.countDown();
                }
            });
        });
        latch.await();
        log.info("【{}】资源下载完毕", productPath);
        //开始合并
        VideoUtils.mergeResource(videoPath, audioPath, productPath);
        return new HashMap<String, String>() {
            {
                put("productUrl", productPath);
                put("coverUrl", coverPath);
            }
        };
    }

    private Request createRequest(String url) {
        return new Request.Builder()
                .url(url)
                .get()
                .addHeader("Connection", "keep-alive")
                .addHeader("User-Agent", DEFAULT_USER_AGENT)
                .addHeader("Accept", "*/*")
                .addHeader("Origin", ORIGIN)
                .addHeader("Sec-Fetch-Site", "cross-site")
                .addHeader("Sec-Fetch-Mode", "cors")
                .addHeader("Sec-Fetch-Dest", "empty")
                .addHeader("Referer", ORIGIN)
                .addHeader("Accept-Encoding", "identity")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                .build();
    }

    private void download(InputStream inputStream, String path) throws IOException {
        log.info("开始下载:{}", path);
        FutureTask futureTask = new FutureTask(() -> {
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path));
            byte[] bytes = new byte[1024];
            int len = -1;
            double total = 0;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                total += len;
            }
            outputStream.flush();
            outputStream.close();
            log.info("{}:下载完毕", path);
            return null;
        });
        try {
            executorService.submit(futureTask);
            futureTask.get(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            log.error("下载{}【超时】失败", path);
            FileUtil.del(path);
        }

    }
}
