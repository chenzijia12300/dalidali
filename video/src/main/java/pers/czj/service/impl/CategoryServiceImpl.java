package pers.czj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pers.czj.dto.CategoryOutputDto;
import pers.czj.entity.Category;
import pers.czj.exception.CategoryException;
import pers.czj.mapper.CategoryMapper;
import pers.czj.service.CategoryService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 创建在 2020/7/10 22:50
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public List<CategoryOutputDto> listCategory() throws CategoryException {
        List<CategoryOutputDto> categoryOutputDtos = baseMapper.listCategory();
        if (CollectionUtils.isEmpty(categoryOutputDtos)){
            throw new CategoryException("视频分类列表为空！");
        }
        return categoryOutputDtos;
    }
}
