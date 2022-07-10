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
import pers.czj.constant.HttpContentTypeEnum;
import pers.czj.constant.VideoPublishStateEnum;
import pers.czj.constant.VideoResolutionEnum;
import pers.czj.dto.VideoBasicOutputDto;
import pers.czj.dto.VideoDetailsOutputDto;
import pers.czj.dto.VideoHotOutputDto;
import pers.czj.entity.Video;
import pers.czj.entity.VideoLog;
import pers.czj.exception.VideoException;
import pers.czj.feign.UserFeignClient;
import pers.czj.mapper.CategoryMapper;
import pers.czj.mapper.VideoLogMapper;
import pers.czj.mapper.VideoMapper;
import pers.czj.service.VideoService;
import pers.czj.util.VideoUtils;
import pers.czj.utils.MinIOUtils;
import pers.czj.utils.RedisUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 创建在 2020/7/11 23:41
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    private static final Logger log = LoggerFactory.getLogger(VideoServiceImpl.class);

    private static final String VIDEO_CONTENT_TYPE = "video/mp4";

    private static final String COVER_CONTENT_TYPE = "image/jpeg";

    private String dir = System.getProperty("user.dir");

    @Autowired
    private MinIOUtils minIOUtils;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private VideoLogMapper logMapper;

    @Value("${redis.category-list}")
    private String categoryListKey;

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.bucket-video-name}")
    private String bucketVideoName;

    @Override
    public VideoDetailsOutputDto findDetailsById(long id) throws VideoException {
/*        VideoDetailsOutputDto detailsOutputDto = baseMapper.findDetailsById(id);
        if (ObjectUtils.isEmpty(detailsOutputDto)){
            throw new VideoException("该视频走丢了~");
        }
        detailsOutputDto.setLog(logMapper.selectOne(new QueryWrapper<VideoLog>().eq("vid",detailsOutputDto.getId())));
        Map<String,Object> infoMap = userFeignClient.findBasicInfoById(detailsOutputDto.getUid());
        detailsOutputDto.setUpImg((String) infoMap.get("uimg"));
        detailsOutputDto.setUpName((String) infoMap.get("username"));
        return detailsOutputDto;*/
        throw new AssertionError("方法被废弃 ");
    }

    @Override
    public VideoDetailsOutputDto findDetailsById(long uid, long vid) throws VideoException {
        VideoDetailsOutputDto detailsOutputDto = baseMapper.findDetailsById(vid);
        if (ObjectUtils.isEmpty(detailsOutputDto)) {
            throw new VideoException("该视频走丢了~");
        }
        detailsOutputDto.setLog(logMapper.selectOne(new QueryWrapper<VideoLog>().eq("vid", detailsOutputDto.getId()).eq("uid", uid)));
        Map<String, Object> infoMap = userFeignClient.findBasicInfoById(uid, detailsOutputDto.getUid());
        log.info("{}", infoMap);
        detailsOutputDto.setUpImg((String) infoMap.get("img"));
        detailsOutputDto.setUpName((String) infoMap.get("username"));
        detailsOutputDto.setFollow((Boolean) infoMap.get("follow"));
        return detailsOutputDto;
    }

    @Override
    public List<VideoBasicOutputDto> listRandomByCategoryPId(long id) throws VideoException {
        PageHelper.startPage(1, 8);
        List<VideoBasicOutputDto> list = baseMapper.listRandomByCategoryPId(id);
        if (CollectionUtils.isEmpty(list)) {
            throw new VideoException("该频道下没有视频。。。");
        }
        return list;
    }

    @Override
    public List<VideoBasicOutputDto> listRandomAll() {
        return baseMapper.listRandomAll();
    }

    @Override
    public List<VideoBasicOutputDto> listSlowRandomAll(int pageSize) {
        PageHelper.startPage(1, pageSize);
        return baseMapper.listSlowRandomAll();
    }

    @Override
    public List<VideoBasicOutputDto> listBasicInfoByIds(Collection<Long> ids) {
        return baseMapper.listBasicInfoByIds(ids);
    }

    @Override
    public List<VideoHotOutputDto> listHotInfoByIds(Collection<Long> ids) {
        return baseMapper.listHotInfoByIds(ids);
    }

    @Override
    public int incrPlayNum(long vid, int num) {
        return baseMapper.incrPlayNum(vid, num);
    }

    @Override
    public int incrCommentNum(long id, int num) {
        return baseMapper.incrCommentNum(id, num);
    }

    @Override
    public int incrDanmuNum(long id, int num) {
        return baseMapper.incrDanmuNum(id, num);
    }

    @Override
    public boolean updatePublishStatus(long id, VideoPublishStateEnum stateEnum) throws FileNotFoundException {

        /*
            获得视频相关信息、原视频地址、对应UUID文件名
         */
        Video video = baseMapper.selectById(id);
        String baseUrl = video.getUrls();
        String fileName = baseUrl.substring(baseUrl.lastIndexOf("/"));
        StringBuilder builder = new StringBuilder();


        //从文件服务器下载到本地，给FFMPEEG使用
        minIOUtils.saveVideoLocalTemp(fileName, dir);


        /*
            生成比原视频低的不同分辨率视频
         */
        VideoResolutionEnum[] states = VideoResolutionEnum.values();
        VideoResolutionEnum state = video.getResolutionState();
        for (int i = 0; state.getCode() > states[i].getCode(); i++) {
            String newFileName = fileName.substring(0, fileName.lastIndexOf(".")) + states[i].getHeight() + fileName.substring(fileName.lastIndexOf("."));
            log.info("fileName:{}", fileName);
            log.info("newFileName:{}", newFileName);
            VideoUtils.createOtherResolutionVideo(dir + fileName, dir + newFileName, states[i].getWidth(), states[i].getHeight());
            minIOUtils.uploadFile(newFileName, new FileInputStream(new File(dir, newFileName)), HttpContentTypeEnum.MP4);
            builder.append(minioUrl + bucketVideoName + "/" + newFileName + ",");
        }
        builder.append(baseUrl);

        //生成预览图
        String previewImage = VideoUtils.createPreviewImage(dir, fileName, 1.0 / (video.getLength() / 10));
        String webPreviewUrl = minIOUtils.uploadFile(previewImage, new FileInputStream(new File(dir, previewImage)), HttpContentTypeEnum.JPEG);
        video.setPreviewUrl(webPreviewUrl);
        log.debug("预览图路径:{}", webPreviewUrl);
        //更新视频对应存储路径
        video.setUrls(builder.toString());
        video.setUpdateTime(new Date());
        updateById(video);


        //更新对应频道总数
        categoryMapper.incrProNum(video.getCategoryPId(), 1);
        /*CategoryOutputDto dto = redisUtils.getItemByList(categoryListKey,video.getCategoryPId(), CategoryOutputDto.class);
        dto.setDayProNum(dto.getDayProNum()+1);
        redisUtils.setItemByList();*/
        return true;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Video findNeedAuditVideo() throws VideoException {
        Video video = baseMapper.findNeedAuditVideo(VideoPublishStateEnum.AUDIT);
        if (ObjectUtils.isEmpty(video)) {
            throw new VideoException("没有视频需要审核~");
        }
        baseMapper.auditing(video.getId(), VideoPublishStateEnum.AUDITING);
        return video;
    }


    @Override
    public boolean save(Video entity) {
        boolean flag = super.save(entity);
        return flag;
    }

}
