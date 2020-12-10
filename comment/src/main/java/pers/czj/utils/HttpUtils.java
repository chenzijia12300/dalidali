package pers.czj.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/**
 * 创建在 2020/12/9 9:17
 */
public class HttpUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    public static String syncGetStr(String url, Map<String, String> headers, Map<String, String> paramMap) {
        log.info("url:{}", url);
        if (StrUtil.isEmpty(url)) {
            return "";
        }


        if (CollectionUtil.isNotEmpty(paramMap)) {
            StringBuilder stringBuilder = new StringBuilder(url);
            stringBuilder.append("?");
            paramMap.forEach((key, value) -> {
                stringBuilder.append(key)
                        .append("=")
                        .append(value)
                        .append("&");
            });
            url = stringBuilder.substring(0, stringBuilder.length() - 1);
        }


        Request.Builder builder = new Request.Builder()
                .url(url)
                .get();
        if (CollectionUtil.isNotEmpty(headers)) {
            headers.forEach((key, value) -> {
                builder.addHeader(key, value);
            });
        }
        OkHttpClient client = new OkHttpClient();
        try {
            Request request = builder.build();
            Response response = client.newCall(request).execute();
            if (response.header("Content-type").equals("text/xml")) {
                return new String(decompress(response.body().bytes()));
//                ObjectInputStream inputStream = new ObjectInputStream(response.body().byteStream());
//                String str = null;
//                while((str = inputStream.readLine())!=null){
//                    log.info("line:{}",str);
//                }
            }

            if (response.code() != 200) {
                log.info("请求失败:{}\nmessage:{}", url, response.message());
                return "";
            }
            String bodyStr = response.body().string();
            response.close();
            return bodyStr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static byte[] decompress(byte[] data) {
        Inflater inflater = new Inflater(true);
        inflater.reset();
        inflater.setInput(data);
        try(ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);){
            byte[] buf = new byte[1024];
            while (!inflater.finished()) {
                int i = inflater.inflate(buf);
                o.write(buf, 0, i);
            }
            return o.toByteArray();
        } catch (DataFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
