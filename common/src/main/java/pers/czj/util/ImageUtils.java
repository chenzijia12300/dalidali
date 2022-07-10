package pers.czj.util;

import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * 创建在 2020/11/9 19:25
 */
public class ImageUtils {

    private static final Logger log = LoggerFactory.getLogger(ImageUtils.class);

    public static File compress(String filePath) {
        int pointIndex = filePath.lastIndexOf('.');
        String outputFilePath = filePath.substring(0, pointIndex) + "compress" + filePath.substring(pointIndex);
        File outputFile = new File(outputFilePath);
        log.info("outputFilePath:{}", outputFilePath);
        try {
            Thumbnails.of(filePath)
                    .scale(0.25f)
                    .toFile(outputFile);
        } catch (IOException e) {
            log.error("没有找到该文件", filePath);
            e.printStackTrace();
        }
        return outputFile;
    }

    public static File compress(File file) {
        return compress(file.getAbsolutePath());
    }
}
