package pers.czj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.czj.dto.DynamicOutputDto;
import pers.czj.entity.Dynamic;

import java.util.List;

/**
 * 创建在 2020/8/9 22:40
 */
public interface DynamicService extends IService<Dynamic> {

    /**
     * @param [uid, startPage, pageSize]
     * @return java.util.List<pers.czj.entity.Dynamic>
     * @author czj
     * 获得相应动态列表
     * @date 2020/8/9 22:54
     */
    public List<DynamicOutputDto> listDynamicByPageAndType(long uid, int startPage, int pageSize, boolean isAll);


    /**
     * @param [uid, did]
     * @return pers.czj.dto.DynamicOutputDto
     * @author czj
     * @date 2020/12/3 19:59
     */
    public DynamicOutputDto findDetailsById(long uid, long did);


    /**
     * 获得用户未读动态的总数
     *
     * @param uid
     * @return
     */
    public int findUnreadCount(long uid);


    /**
     * 自增对应动态主键的评论数量
     *
     * @param [did, num]
     * @return int
     * @author czj
     * @date 2020/11/1 14:35
     */
    public int incrCommentNum(long did, int num);

}
