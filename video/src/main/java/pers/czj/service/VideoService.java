package pers.czj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.czj.constant.VideoPublishStateEnum;
import pers.czj.dto.VideoBasicOutputDto;
import pers.czj.dto.VideoDetailsOutputDto;
import pers.czj.entity.Video;
import pers.czj.exception.VideoException;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
    public List<VideoBasicOutputDto> listRandomByCategoryPId(long id) throws VideoException;


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

    /**
     * @author czj
     * 自增视频评论量
     * @date 2020/7/30 17:24
     * @param [id, num]
     * @return int
     */
    public int incrCommentNum(long id,int num);

    /**
     * @author czj
     * 自增视频弹幕量
     * @date 2020/7/30 17:26
     * @param [id, num]
     * @return int
     */
    public int incrDanmuNum(long id,int num);



    /**
     * @author czj
     * 更改视频发布状态
     * @date 2020/7/23 21:48
     * @param [id, stateEnum]
     * @return boolean
     */
    public boolean updatePublishStatus(long id, VideoPublishStateEnum stateEnum) throws FileNotFoundException;


    /**
     * @author czj
     * 发现需要审核的对象
     * @date 2020/7/23 21:17
     * @param []
     * @return pers.czj.entity.Video
     */
    public Video findNeedAuditVideo() throws VideoException;
}
