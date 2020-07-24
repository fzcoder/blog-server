package com.frankfang.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.frankfang.entity.Category;
import com.frankfang.bean.CategoryWithChildren;

/**
 * 目录服务的接口类
 * @author User
 *
 */
public interface CategoryService extends IService<Category> {
	
	List<CategoryWithChildren> getListWithChildren(Integer parentId, String type);
}
