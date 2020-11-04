package pers.czj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import pers.czj.constant.OrderFieldEnum;
import pers.czj.constant.TableNameEnum;
import pers.czj.dto.CommentOutputDto;
import pers.czj.entity.Comment;
import pers.czj.entity.CommentLog;
import pers.czj.mapper.CommentLogMapper;
import pers.czj.mapper.CommentMapper;
import pers.czj.mapper.ReplyMapper;
import pers.czj.service.CommentService;

import java.util.List;

/**
 * 创建在 2020/7/15 17:45
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    private static final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private CommentLogMapper commentLogMapper;

    @Override
    public List<CommentOutputDto> listComment(TableNameEnum nameEnum, long id, long userId, int pageNum, int pageSize, OrderFieldEnum orderFieldEnum) {

        log.debug("pageNum:{}\npageSize:{},id:{}",pageNum,pageSize,id);
        PageHelper.startPage(pageNum,pageSize);
        List<CommentOutputDto> dtos = baseMapper.listComment(nameEnum.getName(),id,userId,orderFieldEnum.getField());
        PageHelper.startPage(1,2);
        for (CommentOutputDto dto:dtos){
            dto.setReplyList(replyMapper.listReply(nameEnum.getName(),dto.getId(),userId));
        }
        return dtos;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public boolean dynamicHandlerLike(TableNameEnum tableNameEnum, long id, long userId) {
        Long logId = commentLogMapper.select(tableNameEnum.getName(),id,userId);
        if (ObjectUtils.isEmpty(logId)||logId==0){
            log.info("用户{}对评论{}点了个赞",userId,id);
            baseMapper.incrPraiseNum(tableNameEnum.getName(),id,1);
            CommentLog commentLog = new CommentLog(tableNameEnum.getName(),id,userId);
            commentLogMapper.addLog(tableNameEnum.getName(),commentLog);
        }else {
            commentLogMapper.deleteLog(tableNameEnum.getName(),logId);
            log.info("用户{}取消对评论{}的点赞",userId,id);
        }
        return true;
    }

}
