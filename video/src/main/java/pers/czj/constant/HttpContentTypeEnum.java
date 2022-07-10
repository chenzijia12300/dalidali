package pers.czj.constant;

import lombok.Getter;

/**
 * 创建在 2020/10/5 23:12
 */
@Getter
public enum HttpContentTypeEnum {

    MP4("video/mp4"), JPEG("image/jpeg");
    String contentType;

    HttpContentTypeEnum(String contentType) {
        this.contentType = contentType;
    }
}
