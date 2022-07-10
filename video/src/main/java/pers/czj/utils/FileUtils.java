package pers.czj.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import pers.czj.exception.VideoException;

import java.io.File;
import java.io.IOException;

/**
 * 创建在 2020/8/26 18:51
 */

public class FileUtils {

    private static String dir = System.getProperty("user.dir") + "/";

    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    public static void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                if (file.delete()) {
                    log.debug("删除临时文件【{}】成功", file.getPath());
                } else {
                    log.error("删除临时文件【{}】失败", file.getPath());
                }
            } else {
                log.debug("{}文件不存在", file.getPath());
            }
        }
    }

    public static File saveLocalFile(MultipartFile file) throws VideoException {
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        log.info("文件名:{}\t文件后缀:{}", filename, suffix);
        String UUID = java.util.UUID.randomUUID().toString();
        File temp = new File(dir, UUID + suffix);
        try {
            file.transferTo(temp);
            log.info("文件:[{}]存储到临时文件夹成功！", temp.getPath());
        } catch (IOException e) {
            log.error("存储服务器本地出现问题:{}", file.getName());
            throw new VideoException("上传视频出现错误，请重新尝试");
        }
        return temp;
    }
}
