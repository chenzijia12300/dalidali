package pers.czj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.czj.constant.TableNameEnum;
import pers.czj.dto.ReplyOutputDto;
import pers.czj.entity.Reply;

import java.util.List;

/**
 * 创建在 2020/7/15 21:09
 */
public interface ReplyService extends IService<Reply> {

    public List<ReplyOutputDto> listReply(TableNameEnum nameEnum, long id, long userId, int pageNum, int pageSize);
}
