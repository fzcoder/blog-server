package com.fzcoder.opensource.blog.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzcoder.opensource.blog.entity.Category;
import com.fzcoder.opensource.blog.mapper.ICategoryMapper;
import com.fzcoder.opensource.blog.service.CategoryService;
import com.fzcoder.opensource.blog.bean.CategoryWithChildren;

@Service
public class CategoryServiceImpl extends ServiceImpl<ICategoryMapper, Category> implements CategoryService {
	@Override
	public List<CategoryWithChildren> getListWithChildren(Serializable parentId, String type) {
		return getBaseMapper().selectCategoryWithChildren(parentId, type);
	}

    @Override
    public boolean removeWithChildren(Serializable parentId) {
        return getBaseMapper().deleteCategoryWithChildren(parentId) > 0;
    }
}
