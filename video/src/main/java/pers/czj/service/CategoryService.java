package pers.czj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.czj.dto.CategoryOutputDto;
import pers.czj.entity.Category;
import pers.czj.exception.CategoryException;

import java.util.List;

/**
 * 创建在 2020/7/10 22:49
 */
public interface CategoryService extends IService<Category> {

    List<CategoryOutputDto> listCategory() throws CategoryException;
}
