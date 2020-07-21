package pers.czj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import pers.czj.dto.VideoBasicOutputDto;
import pers.czj.dto.VideoDetailsOutputDto;
import pers.czj.entity.Video;
import pers.czj.exception.VideoException;
import pers.czj.mapper.VideoMapper;
import pers.czj.service.VideoService;

import java.util.Collection;
import java.util.List;

/**
 * 创建在 2020/7/11 23:41
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    private static final Logger log = LoggerFactory.getLogger(VideoServiceImpl.class);

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


}
