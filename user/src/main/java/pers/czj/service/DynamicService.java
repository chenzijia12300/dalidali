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
     * @author czj
     * 获得相应动态列表
     * @date 2020/8/9 22:54
     * @param [uid, startPage, pageSize]
     * @return java.util.List<pers.czj.entity.Dynamic>
     */
    public List<DynamicOutputDto> listDynamicByPageAndType(long uid, int startPage, int pageSize,boolean isAll);


    /**
     * 获得用户未读动态的总数
     * @param uid
     * @return
     */
    public int findUnreadCount(long uid);




}
