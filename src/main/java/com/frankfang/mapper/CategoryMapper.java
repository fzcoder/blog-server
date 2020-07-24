package com.frankfang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.frankfang.entity.Category;
import com.frankfang.bean.CategoryWithChildren;

public interface CategoryMapper extends BaseMapper<Category> {

	/**
	 * 查询菜单及其子菜单
	 * 
	 * @param parentId
	 * @param type
	 * @return
	 */
	List<CategoryWithChildren> selectCategoryWithChildren(@Param("parentId") Integer parentId,
			@Param("type") String type);

}
