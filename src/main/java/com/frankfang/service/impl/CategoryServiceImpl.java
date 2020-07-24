package com.frankfang.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frankfang.entity.Category;
import com.frankfang.mapper.CategoryMapper;
import com.frankfang.service.CategoryService;
import com.frankfang.bean.CategoryWithChildren;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

	@Autowired
	private CategoryMapper categoryMapper;

	@Override
	public List<CategoryWithChildren> getListWithChildren(Integer parentId, String type) {
		return categoryMapper.selectCategoryWithChildren(parentId, type);
	}
}
