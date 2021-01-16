package com.fzcoder.service;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzcoder.entity.Category;
import com.fzcoder.bean.CategoryWithChildren;

/**
 * 目录服务的接口类
 * @author User
 *
 */
public interface CategoryService extends IService<Category> {
	
	List<CategoryWithChildren> getListWithChildren(Serializable parentId, String type);

	boolean removeWithChildren(Serializable parentId);
}
