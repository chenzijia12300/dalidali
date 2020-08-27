package pers.czj.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 创建在 2020/8/26 18:51
 */

public class FileUtils {

    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    public static void deleteFile(File ... files){
        for (File file:files){
            if (file.exists()){
                if (file.delete()){
                    log.debug("删除临时文件【{}】成功",file.getPath());
                }else {
                    log.error("删除临时文件【{}】失败",file.getPath());
                }
            }else {
                log.debug("{}文件不存在",file.getPath());
            }
        }
    }
}
