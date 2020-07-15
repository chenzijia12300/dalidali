package pers.czj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.czj.constant.TableNameEnum;
import pers.czj.dto.CommentOutputDto;
import pers.czj.entity.Comment;
import pers.czj.mapper.CommentMapper;
import pers.czj.mapper.ReplyMapper;
import pers.czj.service.CommentService;

import java.util.List;

/**
 * 创建在 2020/7/15 17:45
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private ReplyMapper replyMapper;

    @Override
    public List<CommentOutputDto> listComment(TableNameEnum nameEnum,long id,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<CommentOutputDto> dtos = baseMapper.listComment(nameEnum.getName(),id);
        PageHelper.startPage(1,2);
        for (CommentOutputDto dto:dtos){
            dto.setReplyList(replyMapper.listReply(nameEnum.getName(),dto.getId()));
        }
        return dtos;
    }
}
