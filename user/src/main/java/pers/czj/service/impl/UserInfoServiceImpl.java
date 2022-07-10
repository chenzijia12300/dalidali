package pers.czj.service.impl;

import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.czj.feign.VideoFeignClient;
import pers.czj.mapper.UserInfoMapper;
import pers.czj.service.UserInfoService;

import java.util.List;

/**
 * 创建在 2020/12/2 11:38
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private static final Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    private VideoFeignClient videoFeignClient;

    private UserInfoMapper userInfoMapper;

    @Autowired
    public UserInfoServiceImpl(VideoFeignClient videoFeignClient, UserInfoMapper userInfoMapper) {
        this.videoFeignClient = videoFeignClient;
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public List findPublishVideoInfo(long uid, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Long> videoIds = userInfoMapper.findPublishVideoByUid(uid);
        return videoFeignClient.listBasicVideoInfoByIds(videoIds);
    }

    @Override
    public List findCollectVideoInfo(long uid, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Long> videoIds = userInfoMapper.findCollectVideoByUid(uid);
        return videoFeignClient.listBasicVideoInfoByIds(videoIds);
    }

    @Override
    public List findHasCoinVideoInfo(long uid, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Long> videoIds = userInfoMapper.findHasCoinVideoByUid(uid);
        return videoFeignClient.listBasicVideoInfoByIds(videoIds);
    }

    @Override
    public List findPraiseCoinVideoInfo(long uid, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Long> videoIds = userInfoMapper.findPraiseCoinVideoByUid(uid);
        return videoFeignClient.listBasicVideoInfoByIds(videoIds);
    }
}
