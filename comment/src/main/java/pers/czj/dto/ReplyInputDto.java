package pers.czj.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import pers.czj.constant.TableNameEnum;
import pers.czj.entity.Reply;

import javax.validation.constraints.NotEmpty;

/**
 * 创建在 2020/7/18 14:25
 */
@Data
@ApiModel
public class ReplyInputDto {

    private long id;

    private long cid;

    private long ruid;

    @NotEmpty
    private String content;

    private TableNameEnum tableNameEnum;

    public Reply convert(){
        Reply reply = new Reply();
        BeanUtils.copyProperties(this,reply);
        reply.setTableName(tableNameEnum.getName());
        return reply;
    }
}
