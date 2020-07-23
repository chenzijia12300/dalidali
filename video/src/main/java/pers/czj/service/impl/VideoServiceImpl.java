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
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import pers.czj.constant.VideoPublishStateEnum;
import pers.czj.constant.VideoResolutionEnum;
import pers.czj.dto.VideoBasicOutputDto;
import pers.czj.dto.VideoDetailsOutputDto;
import pers.czj.entity.Video;
import pers.czj.exception.VideoException;
import pers.czj.mapper.VideoMapper;
import pers.czj.service.VideoService;
import pers.czj.util.VideoUtils;
import pers.czj.utils.MinIOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 创建在 2020/7/11 23:41
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    private static final Logger log = LoggerFactory.getLogger(VideoServiceImpl.class);

    private String dir = System.getProperty("user.dir");

    @Autowired
    private MinIOUtils minIOUtils;

    @Override
    public VideoDetailsOutputDto findDetailsById(long id) throws VideoException {
        VideoDetailsOutputDto detailsOutputDto = baseMapper.findDetailsById(id);
        if (ObjectUtils.isEmpty(detailsOutputDto)){
            throw new VideoException("该视频走丢了~");
        }
        return detailsOutputDto;
    }

    @Override
    public List<VideoDetailsOutputDto> listRandomByCategoryPId(long id) throws VideoException {
        List<VideoDetailsOutputDto> list = baseMapper.listRandomByCategoryPId(id);
        if (CollectionUtils.isEmpty(list)){
            throw new VideoException("该频道下没有视频。。。");
        }
        return list;
    }

    @Override
    public List<VideoBasicOutputDto> listBasicInfoByIds(Collection<Long> ids) {
        return baseMapper.listBasicInfoByIds(ids);
    }

    @Override
    public int incrPlayNum(long vid, int num) {
        return baseMapper.incrPlayNum(vid,num);
    }

    @Override
    public boolean updatePublishStatus(long id, VideoPublishStateEnum stateEnum) throws FileNotFoundException {
        Video video = baseMapper.selectById(id);
        String baseUrl = video.getUrls();
        String fileName = baseUrl.substring(baseUrl.lastIndexOf("/"));
        minIOUtils.saveLocalTemp(fileName,dir);
        VideoResolutionEnum[]states = VideoResolutionEnum.values();
        VideoResolutionEnum state = video.getResolutionState();
        StringBuilder builder = new StringBuilder(baseUrl+",");
        for(int i = 0;state.getCode()>states[i].getCode();i++){
            String newFileName = fileName.substring(0,fileName.lastIndexOf("."))+states[i].getHeight()+fileName.substring(fileName.lastIndexOf("."));
            log.info("fileName:{}",fileName);
            log.info("newFileName:{}",newFileName);
            VideoUtils.createOtherResolutionVideo(dir+fileName,dir+newFileName,states[i].getWidth(),states[i].getHeight());
            minIOUtils.uploadFile(newFileName,new FileInputStream(new File(dir,newFileName)));
            builder.append(dir+newFileName+",");
        }
        video.setUrls(builder.substring(0,builder.length()-1));
        video.setUpdateTime(new Date());
        updateById(video);
        return true;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public Video findNeedAuditVideo() throws VideoException {
        Video video = baseMapper.findNeedAuditVideo(VideoPublishStateEnum.AUDIT);
        if (ObjectUtils.isEmpty(video)){
            throw new VideoException("没有视频需要审核~");
        }
        baseMapper.auditing(video.getId(),VideoPublishStateEnum.AUDITING);
        return video;
    }


}
