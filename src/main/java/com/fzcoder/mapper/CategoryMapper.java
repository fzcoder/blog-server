package com.fzcoder.mapper;

import java.util.List;

import com.fzcoder.view.CategoryView;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fzcoder.entity.Category;
import com.fzcoder.bean.CategoryWithChildren;

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

	CategoryView selectViewObjectById(Integer categoryId);

	/**
	 * 删除该目录及其子目录
	 * @param parentId 父目录id
	 * @return
	 */
	int deleteCategoryWithChildren(@Param("parentId") Integer parentId);

}
