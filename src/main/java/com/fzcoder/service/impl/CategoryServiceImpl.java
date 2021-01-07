package com.fzcoder.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzcoder.entity.Category;
import com.fzcoder.mapper.CategoryMapper;
import com.fzcoder.service.CategoryService;
import com.fzcoder.bean.CategoryWithChildren;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

	@Autowired
	private CategoryMapper categoryMapper;

	@Override
	public List<CategoryWithChildren> getListWithChildren(Integer parentId, String type) {
		return categoryMapper.selectCategoryWithChildren(parentId, type);
	}

    @Override
    public boolean removeWithChildren(Integer parentId) {
        return categoryMapper.deleteCategoryWithChildren(parentId) > 0;
    }
}
