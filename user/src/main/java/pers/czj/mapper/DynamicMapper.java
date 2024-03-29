package pers.czj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import pers.czj.dto.DynamicOutputDto;
import pers.czj.entity.Dynamic;

import java.util.List;

/**
 * 创建在 2020/8/9 22:39
 */
public interface DynamicMapper extends BaseMapper<Dynamic> {

    /**
     * 返回最新动态列表
     *
     * @param [uid,ids]
     * @return java.util.List<pers.czj.dto.DynamicOutputDto>
     * @author czj
     * @date 2020/10/9 20:36
     */
    public List<DynamicOutputDto> listNewDynamic(@Param("uid") long uid, @Param("ids") List<Long> ids);


    public DynamicOutputDto findDetailsById(@Param("uid") long uid, @Param("did") long did);


    /**
     * 返回视频类型最新动态列表
     *
     * @param [uid, ids]
     * @return java.util.List<pers.czj.dto.DynamicOutputDto>
     * @author czj
     * @date 2020/10/26 20:23
     */
    public List<DynamicOutputDto> listNewVideoDynamic(@Param("uid") long uid, @Param("ids") List<Long> ids);

    /**
     * 添加/减少点赞数
     *
     * @param id  主键
     * @param num 添加/减少数字
     * @return int
     * @author czj
     * @date 2020/10/14 16:26
     */
    @Update("UPDATE dynamic SET praise_num = praise_num + #{num} WHERE id = #{id}")
    public int incrPraiseNum(long id, int num);

    /**
     * 添加/减少评论数
     *
     * @param id  主键
     * @param num 添加/减少数字
     * @return int
     * @author czj
     * @date 2020/10/14 16:26
     */
    @Update("UPDATE dynamic SET comment_num = comment_num + #{num} WHERE id = #{id}")
    public int incrCommentNum(long id, int num);

}
