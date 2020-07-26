package pers.czj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import pers.czj.constant.VideoPublishStateEnum;
import pers.czj.constant.VideoResolutionEnum;
import pers.czj.dto.CategoryOutputDto;
import pers.czj.dto.VideoBasicOutputDto;
import pers.czj.dto.VideoDetailsOutputDto;
import pers.czj.entity.Video;
import pers.czj.exception.VideoException;
import pers.czj.mapper.CategoryMapper;
import pers.czj.mapper.VideoMapper;
import pers.czj.service.VideoService;
import pers.czj.util.VideoUtils;
import pers.czj.utils.MinIOUtils;
import pers.czj.utils.RedisUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
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

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private CategoryMapper categoryMapper;

    @Value("${redis.category-list}")
    private String categoryListKey;

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
        PageHelper.startPage(1,8);
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

        /*
            获得视频相关信息、原视频地址、对应UUID文件名
         */
        Video video = baseMapper.selectById(id);
        String baseUrl = video.getUrls();
        String fileName = baseUrl.substring(baseUrl.lastIndexOf("/"));
        StringBuilder builder = new StringBuilder(baseUrl+",");


        //从文件服务器下载到本地，给FFMPEEG使用
        minIOUtils.saveLocalTemp(fileName,dir);


        /*
            生成比原视频低的不同分辨率视频
         */
        VideoResolutionEnum[]states = VideoResolutionEnum.values();
        VideoResolutionEnum state = video.getResolutionState();
        for(int i = 0;state.getCode()>states[i].getCode();i++){
            String newFileName = fileName.substring(0,fileName.lastIndexOf("."))+states[i].getHeight()+fileName.substring(fileName.lastIndexOf("."));
            log.info("fileName:{}",fileName);
            log.info("newFileName:{}",newFileName);
            VideoUtils.createOtherResolutionVideo(dir+fileName,dir+newFileName,states[i].getWidth(),states[i].getHeight());
            minIOUtils.uploadFile(newFileName,new FileInputStream(new File(dir,newFileName)));
            builder.append(dir+newFileName+",");
        }

        //更新视频对应存储路径
        video.setUrls(builder.substring(0,builder.length()-1));
        video.setUpdateTime(new Date());
        updateById(video);


        //更新对应频道总数
        categoryMapper.incrProNum(video.getCategoryPId(),1);
        /*CategoryOutputDto dto = redisUtils.getItemByList(categoryListKey,video.getCategoryPId(), CategoryOutputDto.class);
        dto.setDayProNum(dto.getDayProNum()+1);
        redisUtils.setItemByList();*/
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


    @Override
    public boolean save(Video entity) {
        boolean flag = super.save(entity);
        return flag;
    }

}
