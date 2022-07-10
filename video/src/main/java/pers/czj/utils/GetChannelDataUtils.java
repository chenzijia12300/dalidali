package pers.czj.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.czj.entity.Category;
import pers.czj.service.CategoryService;

import java.io.IOException;
import java.util.List;

/**
 * 创建在 2020/11/30 17:24
 */
@Component
public class GetChannelDataUtils {

    public static final String CHANNEL_URL = "https://app.bilibili.com/x/v2/channel/region/list?appkey=1d8b6e7d45233436&build=6140500&c_locale=zh-Hans_CN&channel=bili&mobi_app=android&platform=android&s_locale=zh-Hans_CN&statistics=%7B%22appId%22%3A1%2C%22platform%22%3A3%2C%22version%22%3A%226.14.0%22%2C%22abtest%22%3A%22%22%7D&teenagers_mode=0&ts=1606723564&sign=ccace7561669af02805af59f75549f20";

    private static final Logger log = LoggerFactory.getLogger(GetChannelDataUtils.class);

    @Autowired
    private CategoryService categoryService;

    public void addChannelsInfoByWeb() {

        Request request = new Request.Builder()
                .url(CHANNEL_URL)
                .get()
                .build();
        new OkHttpClient()
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        log.error("爬取频道失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String bodyStr = response.body().string();
                        log.info("body:{}", bodyStr);
                        JSONObject jsonObject = JSON.parseObject(bodyStr);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        List<Category> channelBeans = jsonArray.toJavaList(Category.class);
                        log.info("channelBean:{}", channelBeans);
                        categoryService.saveBatch(channelBeans);
                    }
                });

    }


    @Data
    public static class ChannelBean {

        private String name;

        private String logo;

    }


}
