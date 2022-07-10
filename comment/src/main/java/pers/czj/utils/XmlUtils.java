package pers.czj.utils;

import cn.hutool.core.collection.ListUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import pers.czj.constant.DanmuLocationEnum;
import pers.czj.entity.Danmu;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建在 2020/12/9 9:35
 */
public class XmlUtils {


    public static List<Danmu> parse(String str, long vid) {
        try {
            List<Danmu> list = new ArrayList<>();
            DecimalFormat format = new DecimalFormat("#.00");
            Document document = DocumentHelper.parseText(str);
            List<Element> elements = document.getRootElement()
                    .elements("d");
            elements.forEach(element -> {
                String attributeStr = element.attribute("p").getValue();
                String[] attributes = attributeStr.split(",");
                Danmu danmu = new Danmu();
                danmu.setColor("#" + Integer.toHexString(Integer.parseInt(attributes[3])));
                danmu.setLocation(attributes[1].equals("5") ? DanmuLocationEnum.TOP : DanmuLocationEnum.STANDARD);
                try {
                    danmu.setContent(new String(element.getText().getBytes(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                danmu.setVid(vid);
                danmu.setShowSecond((long) (Double.parseDouble(attributes[0]) * 1000));
                list.add(danmu);
            });
            return list;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return ListUtil.empty();
    }

}
