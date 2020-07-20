package pers.czj.dto;

import lombok.Data;

/**
 * 创建在 2020/7/20 22:50
 */
@Data
public class UserCollectionLogInputDto {

    private long uid;

    private long vid;

    private boolean isAdd;

    public UserCollectionLogInputDto(long uid, long vid, boolean isAdd) {
        this.uid = uid;
        this.vid = vid;
        this.isAdd = isAdd;
    }

    public UserCollectionLogInputDto() {
    }
}
