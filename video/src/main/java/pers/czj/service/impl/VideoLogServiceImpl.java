package pers.czj.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import pers.czj.entity.VideoLog;
import pers.czj.mapper.VideoLogMapper;
import pers.czj.service.VideoLogService;

/**
 * 创建在 2020/7/19 21:47
 */
@Service
public class VideoLogServiceImpl extends ServiceImpl<VideoLogMapper, VideoLog>implements VideoLogService{

    private static final Logger log = LoggerFactory.getLogger(VideoLogServiceImpl.class);

    @Override
    public boolean handlerVideoLog(VideoLog entity) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("vid",entity.getVid());
        queryWrapper.eq("uid",entity.getUid());
        VideoLog videoLog = getOne(queryWrapper);
        if(ObjectUtils.isEmpty(videoLog)){
            log.info("用户{}对视频{}进行了添加操作",entity.getUid(),entity.getVid());
            save(entity);
        }else {
            entity.setId(videoLog.getId());
            updateById(entity);
            log.info("用户{}对视频{}进行了更改操作",entity.getUid(),entity.getVid());
        }
        return true;
    }
}
