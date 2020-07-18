package pers.czj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import pers.czj.dto.ReplyOutputDto;
import pers.czj.entity.Reply;

import java.util.List;

/**
 * 创建在 2020/7/15 18:36
 */
public interface ReplyMapper extends BaseMapper<Reply> {

    /**
     * @author czj
     * @date 2020/7/15 18:37
     * @param [tableName, id, userId]
     * @return java.util.List<pers.czj.dto.ReplyOutputDto>
     */
    public List<ReplyOutputDto> listReply(String tableName,long cid,long userId);
}
