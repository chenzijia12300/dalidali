package pers.czj;

import static org.junit.Assert.assertTrue;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import org.junit.Test;

import java.io.File;
import java.util.Hashtable;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        File file = new File("E:\\ideaProject\\dalidali/925c71d9-fdfc-4ee8-b92e-e3baa55f1302.gif");
        System.out.println(FileUtil.del("E:\\ideaProject\\dalidali\\925c71d9-fdfc-4ee8-b92e-e3baa55f1302.gif"));
        System.out.println(file.exists());
    }


    @Test
    public void testStrUtil(){
        String s = "e:/file/text.txt";
        System.out.println(StrUtil.subAfter(s,"/",false));
    }


    @Test
    public void testFileUtil(){
        System.out.println(FileUtil.exist("D:/temp/response1.json"));
    }

}
