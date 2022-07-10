package pers.czj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import pers.czj.dto.UserCollectionLogInputDto;
import pers.czj.entity.VideoLog;
import pers.czj.exception.UserException;
import pers.czj.exception.VideoException;
import pers.czj.feign.UserFeignClient;
import pers.czj.mapper.VideoLogMapper;
import pers.czj.mapper.VideoMapper;
import pers.czj.service.VideoLogService;

/**
 * 创建在 2020/7/19 21:47
 */
@Service
public class VideoLogServiceImpl extends ServiceImpl<VideoLogMapper, VideoLog> implements VideoLogService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private UserFeignClient userFeignClient;

    private static final int MAX_COIN_NUM = 2;

    private static final Logger log = LoggerFactory.getLogger(VideoLogServiceImpl.class);

    @Override
    public boolean handlerVideoLog(VideoLog entity) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("vid", entity.getVid());
        queryWrapper.eq("uid", entity.getUid());
        VideoLog videoLog = getOne(queryWrapper);
        if (ObjectUtils.isEmpty(videoLog)) {
            log.info("用户{}对视频{}进行了添加操作", entity.getUid(), entity.getVid());
            save(entity);
        } else {
            entity.setId(videoLog.getId());
            updateById(entity);
            log.info("用户{}对视频{}进行了更改操作", entity.getUid(), entity.getVid());
        }
        return true;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public boolean dynamicLike(long vid, long uid) {
        VideoLog videoLog = getVideo(vid, uid);
        if (ObjectUtils.isEmpty(videoLog)) {
            videoLog = new VideoLog().setVid(vid).setUid(uid).setIsPraise(true);
            save(videoLog);
            videoMapper.incrPraiseNum(vid, 1);
            log.info("用户{}对视频{}进行了插入赞操作", uid, vid);
        } else {
            boolean flag = !videoLog.getIsPraise();
            videoLog.setIsPraise(flag);
            updateById(videoLog);
            videoMapper.incrPraiseNum(vid, flag ? 1 : -1);
            log.info("用户{}对视频{}进行了更新赞操作", uid, vid);
        }
        return true;
    }

    @Override
    public boolean addCoins(long vid, long uid, int num) throws VideoException, UserException {
        int userCoinNum = userFeignClient.findCoinNumById(uid);
        if (userCoinNum < num) {
            throw new UserException("你没有足够的硬币喔~");
        }
        VideoLog videoLog = getVideo(vid, uid);
        if (ObjectUtils.isEmpty(videoLog)) {
            videoLog = new VideoLog().setVid(vid).setUid(uid).setCoinNum(num);
            save(videoLog);
        } else {
            int coinNum = videoLog.getCoinNum();
            if (coinNum >= MAX_COIN_NUM || coinNum + num > MAX_COIN_NUM) {
                throw new VideoException("投币数已经到上限拉~");
            }
            videoLog.setCoinNum(coinNum + num);
            updateById(videoLog);
        }
        videoMapper.incrCoinNum(vid, num);
        userFeignClient.incrCoinNumById(uid, -num);
        log.info("用户{}给视频{}投了{}个硬币", uid, vid, videoLog.getCoinNum());
        return true;
    }

    @Override
    public boolean dynamicCollection(long vid, long uid) {
        VideoLog videoLog = getVideo(vid, uid);
        if (ObjectUtils.isEmpty(videoLog)) {
            videoLog = new VideoLog().setVid(vid).setUid(uid).setIsCollection(true);
            save(videoLog);
            log.info("用户{}对视频{}进行了收藏操作", uid, vid);

        } else {
            log.debug("isCollection:{}", videoLog.getIsCollection());
            videoLog.setIsCollection(!videoLog.getIsCollection());
            updateById(videoLog);
        }
        UserCollectionLogInputDto dto = new UserCollectionLogInputDto(uid, vid, videoLog.getIsCollection());
        userFeignClient.dynamicCollection(dto);
        return true;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @Override
    public boolean allOperate(long vid, long uid) throws VideoException, UserException {
        dynamicLike(vid, uid);
        addCoins(vid, uid, 2);
        dynamicCollection(vid, uid);
        return true;
    }


    private VideoLog getVideo(long vid, long uid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("vid", vid);
        queryWrapper.eq("uid", uid);
        return getOne(queryWrapper);
    }
}
