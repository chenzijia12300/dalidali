package pers.czj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.czj.constant.ActionType;
import pers.czj.entity.Message;

import java.util.List;

/**
 * 创建在 2020/8/10 21:35
 */
public interface MessageService extends IService<Message> {


    /**
     * @author czj
     * 查询对应type的消息列表
     * @date 2020/8/10 21:46
     * @param [uid, type, pageNum, pageSize]
     * @return java.util.List<pers.czj.entity.Message>
     */
    public List<Message> listMessageByType(long uid, ActionType type,int pageNum,int pageSize);


    /**
     * 获得用户未读动态的总数
     * @param uid
     * @return
     */
    public int findUnreadCount(long uid);
}
