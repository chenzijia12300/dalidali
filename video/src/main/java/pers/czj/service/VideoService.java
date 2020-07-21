package pers.czj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.czj.dto.VideoBasicOutputDto;
import pers.czj.dto.VideoDetailsOutputDto;
import pers.czj.entity.Video;
import pers.czj.exception.VideoException;

import java.util.Collection;
import java.util.List;

/**
 * 创建在 2020/7/11 23:40
 */
public interface VideoService extends IService<Video> {
    /**
     * @author czj
     * 根据id获得视频详细信息
     * @date 2020/7/13 19:32
     * @param [id]
     * @return pers.czj.dto.VideoDetailsOutputDto
     */
    public VideoDetailsOutputDto findDetailsById(long id) throws VideoException;

    /**
     * @author czj
     * 根据顶级频道主键获得视频信息
     * @date 2020/7/13 21:57
     * @param [id]
     * @return java.util.List<pers.czj.dto.VideoDetailsOutputDto>
     */
    public List<VideoDetailsOutputDto> listRandomByCategoryPId(long id) throws VideoException;


    /**
     * @author czj
     * 根据主键们获得视频的基本信息
     * @date 2020/7/17 10:28
     * @param [ids]
     * @return java.util.List<pers.czj.dto.VideoDetailsOutputDto>
     */
    public List<VideoBasicOutputDto> listBasicInfoByIds(Collection<Long> ids);

    /**
     * @author czj
     * 自增视频播放量
     * @date 2020/7/21 15:43
     * @param [vid, num]
     * @return boolean
     */
    public int incrPlayNum(long vid,int num);
}
