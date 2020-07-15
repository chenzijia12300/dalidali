package pers.czj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import pers.czj.constant.TableNameEnum;
import pers.czj.dto.ReplyOutputDto;
import pers.czj.entity.Reply;
import pers.czj.mapper.ReplyMapper;
import pers.czj.service.ReplyService;

import java.util.List;

/**
 * 创建在 2020/7/15 21:09
 */
@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply> implements ReplyService {

    @Override
    public List<ReplyOutputDto> listReply(TableNameEnum nameEnum, long id, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<ReplyOutputDto> dtos = baseMapper.listReply(nameEnum.getName(),id);
        return dtos;
    }
}
