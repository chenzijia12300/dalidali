package pers.czj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import pers.czj.dto.CategoryOutputDto;
import pers.czj.entity.Category;

import java.util.List;

/**
 * 创建在 2020/7/10 22:49
 */
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * @author czj
     * 获得视频全部分类列表
     * @date 2020/7/11 14:45
     * @param []
     * @return java.util.List<pers.czj.dto.CategoryOutputDto>
     */
    List<CategoryOutputDto> listCategory();

    /**
     * @author czj
     * 根据父类id查询分类列表
     * @date 2020/7/11 14:46
     * @param [pid]
     * @return java.util.List<pers.czj.dto.CategoryOutputDto>
     */
    List<CategoryOutputDto> findCategoryByPid(int pid);

}
