package pers.czj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.czj.dto.CategoryOutputDto;
import pers.czj.dto.CategoryTopOutputDto;
import pers.czj.entity.Category;
import pers.czj.exception.CategoryException;

import java.util.List;
import java.util.Map;

/**
 * 创建在 2020/7/10 22:49
 */
public interface CategoryService extends IService<Category> {


    /**
     * 返回所有频道，包括其子频道
     * @author czj
     * @date 2020/11/30 19:56
     * @param []
     * @return java.util.List<pers.czj.dto.CategoryOutputDto>
     */
    List<CategoryOutputDto> listCategory() throws CategoryException;


    /**
     * 返回顶级频道
     * @author czj
     * @date 2020/11/30 19:58
     * @param []
     * @return java.util.List<pers.czj.dto.CategoryTopOutputDto>
     */
    List<CategoryTopOutputDto> listTopCategory();
}
