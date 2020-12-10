package pers.czj.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.math.MathUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.HexUtil;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.xml.sax.SAXException;
import pers.czj.constant.DanmuLocationEnum;
import pers.czj.entity.Danmu;

import javax.swing.text.html.parser.DocumentParser;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 创建在 2020/12/9 9:35
 */
public class XmlUtils {


    public static List<Danmu> parse(String str,long vid){
        try {
            List<Danmu> list = new ArrayList<>();
            DecimalFormat format = new DecimalFormat("#.00");
            Document document = DocumentHelper.parseText(str);
            List<Element> elements = document.getRootElement()
                    .elements("d");
            elements.forEach(element -> {
                String attributeStr = element.attribute("p").getValue();
                String[]attributes = attributeStr.split(",");
                Danmu danmu = new Danmu();
                danmu.setColor("#"+Integer.toHexString(Integer.parseInt(attributes[3])));
                danmu.setLocation(attributes[1].equals("5")?DanmuLocationEnum.TOP:DanmuLocationEnum.STANDARD);
                try {
                    danmu.setContent(new String(element.getText().getBytes(),"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                danmu.setVid(vid);
                danmu.setShowSecond((long) (Double.parseDouble(attributes[0])*1000));
                list.add(danmu);
            });
            return list;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return ListUtil.empty();
    }

}
