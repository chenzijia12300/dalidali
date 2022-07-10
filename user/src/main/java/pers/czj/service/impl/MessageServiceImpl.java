package pers.czj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import pers.czj.constant.ActionType;
import pers.czj.entity.Message;
import pers.czj.mapper.MessageMapper;
import pers.czj.mapper.UserMapper;
import pers.czj.service.MessageService;

import java.util.Date;
import java.util.List;

/**
 * 创建在 2020/8/10 21:37
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Message> listMessageByType(long uid, ActionType type, int pageNum, int pageSize) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("receive_uid", uid);
        queryWrapper.orderByDesc("create_time");
        if (!ObjectUtils.isEmpty(type)) {
            queryWrapper.eq("type", type);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Message> messages = baseMapper.selectList(queryWrapper);
        return messages;
    }

    @Override
    public int findUnreadCount(long uid) {
        Date lastTime = userMapper.findLastReadMessageTime(uid);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.gt("create_time", lastTime);
        wrapper.eq("receive_uid", uid);
        return count(wrapper);
    }
}
