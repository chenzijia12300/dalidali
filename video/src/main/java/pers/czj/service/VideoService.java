package pers.czj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.czj.constant.VideoPublishStateEnum;
import pers.czj.dto.VideoBasicOutputDto;
import pers.czj.dto.VideoDetailsOutputDto;
import pers.czj.dto.VideoHotOutputDto;
import pers.czj.entity.Video;
import pers.czj.exception.VideoException;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;

/**
 * 创建在 2020/7/11 23:40
 */
public interface VideoService extends IService<Video> {
    /**
     * 根据id获得视频详细信息
     *
     * @param [id]
     * @return pers.czj.dto.VideoDetailsOutputDto
     * @author czj
     * @date 2020/7/13 19:32
     */
    @Deprecated
    public VideoDetailsOutputDto findDetailsById(long id) throws VideoException;

    /**
     * 获得视频详细信息
     *
     * @param uid 用户主键
     * @param vid 视频主键
     * @return pers.czj.dto.VideoDetailsOutputDto
     * @author czj
     * @date 2020/7/13 19:32
     */
    public VideoDetailsOutputDto findDetailsById(long uid, long vid) throws VideoException;


    /**
     * @param [id]
     * @return java.util.List<pers.czj.dto.VideoDetailsOutputDto>
     * @author czj
     * 根据顶级频道主键获得视频信息
     * @date 2020/7/13 21:57
     */
    public List<VideoBasicOutputDto> listRandomByCategoryPId(long id) throws VideoException;


    /**
     * 随机返回8条连续数据，随机化不好，但效率快
     *
     * @param []
     * @return java.util.List<pers.czj.dto.VideoBasicOutputDto>
     * @author czj
     * @date 2020/10/6 12:02
     */
    @Deprecated
    public List<VideoBasicOutputDto> listRandomAll();


    /**
     * 随机返回8条数据，随机化好，但效率慢出屎
     *
     * @param [pageNum, pageSize]
     * @return java.util.List<pers.czj.dto.VideoBasicOutputDto>
     * @author czj
     * @date 2020/10/6 13:16
     */
    public List<VideoBasicOutputDto> listSlowRandomAll(int pageSize);


    /**
     * 根据主键们获得视频的基本信息
     *
     * @param [ids]
     * @return java.util.List<pers.czj.dto.VideoDetailsOutputDto>
     * @author czj
     * @date 2020/7/17 10:28
     */
    public List<VideoBasicOutputDto> listBasicInfoByIds(Collection<Long> ids);


    /**
     * 根据主键们获得热门视频所需信息
     *
     * @param [ids]
     * @return java.util.List<pers.czj.dto.VideoHotOutputDto>
     * @author czj
     * @date 2020/11/11 19:58
     */
    public List<VideoHotOutputDto> listHotInfoByIds(Collection<Long> ids);


    /**
     * @param [vid, num]
     * @return boolean
     * @author czj
     * 自增视频播放量
     * @date 2020/7/21 15:43
     */
    public int incrPlayNum(long vid, int num);

    /**
     * @param [id, num]
     * @return int
     * @author czj
     * 自增视频评论量
     * @date 2020/7/30 17:24
     */
    public int incrCommentNum(long id, int num);

    /**
     * @param [id, num]
     * @return int
     * @author czj
     * 自增视频弹幕量
     * @date 2020/7/30 17:26
     */
    public int incrDanmuNum(long id, int num);


    /**
     * @param [id, stateEnum]
     * @return boolean
     * @author czj
     * 更改视频发布状态
     * @date 2020/7/23 21:48
     */
    public boolean updatePublishStatus(long id, VideoPublishStateEnum stateEnum) throws FileNotFoundException;


    /**
     * @param []
     * @return pers.czj.entity.Video
     * @author czj
     * 发现需要审核的对象
     * @date 2020/7/23 21:17
     */
    public Video findNeedAuditVideo() throws VideoException;


}
