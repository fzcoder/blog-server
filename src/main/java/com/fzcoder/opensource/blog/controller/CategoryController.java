package com.fzcoder.opensource.blog.controller;

import java.io.Serializable;
import java.util.Map;

import com.fzcoder.opensource.blog.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzcoder.opensource.blog.entity.Category;
import com.fzcoder.opensource.blog.bean.PageRequest;
import com.fzcoder.opensource.blog.bean.JsonResponse;
import com.fzcoder.opensource.blog.service.CategoryService;
import com.fzcoder.opensource.blog.utils.HttpUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	// 查询字段
	private static final String[] SQLSELECT_LIST = { "id", "parent_id", "name", "level", "description",
			"icon", "type" };

	@PostMapping("/admin/category")
	public Object addCategory(@RequestBody Category category) {
		String id = IdGenerator.createIdBy62BaseRandom(6);
		QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id", id);
		while (categoryService.count(queryWrapper) > 0) {
			id = IdGenerator.createIdBy62BaseRandom(6);
			queryWrapper.eq("id", id);
		}
		category.setId(id);
		// 添加记录
		if (categoryService.save(category)) {
			return new JsonResponse(HttpUtils.Status_OK, "添加目录成功！");
		} else {
			log.error("添加目录操作异常！");
			return new JsonResponse(HttpUtils.Status_BadRequest, "添加目录失败！");
		}
	}

	@GetMapping("/admin/category/{id}")
	public Object getOne(@PathVariable("id") String id) {
		return new JsonResponse(categoryService.getById(id));
	}

	@GetMapping("/category/menu")
	public Object getCategoryMenu(@RequestParam Map<String, Object> params) {
		if (!params.containsKey("level")) {
			return new JsonResponse(categoryService.getListWithChildren("0", params.get("type").toString()));
		} else {
			QueryWrapper<Category> wrapper = new QueryWrapper<>();
			wrapper.allEq(true, params, false);
			String[] sqlSelect = { "id", "name" };
			wrapper.select(sqlSelect);
			return new JsonResponse(categoryService.listMaps(wrapper));
		}
	}

	@PostMapping("/category")
	public Object getList(@RequestParam Map<String, Object> params, @RequestBody PageRequest pageRequest) {
		// 1. 生成条件构造器
		QueryWrapper<Category> wrapper = new QueryWrapper<>();
		wrapper.allEq(true, params, false);
		wrapper.like(true, "name", pageRequest.getKey());
		wrapper.select(SQLSELECT_LIST);
		wrapper.orderBy(true, !pageRequest.isReverse(), pageRequest.getOrderBy());
		// 2. 分页查询
		IPage<Map<String, Object>> page = categoryService
				.pageMaps(new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize()), wrapper);
		// 3. 返回结果
		return new JsonResponse(page);
	}

	@PutMapping("/admin/category")
	public Object updateCategory(@RequestBody Category category) {
		if (categoryService.updateById(category)) {
			return new JsonResponse(HttpUtils.Status_OK, "修改目录成功！");
		} else {
			log.error("修改目录操作异常！");
			return new JsonResponse(HttpUtils.Status_BadRequest, "修改目录失败！");
		}
	}

	@DeleteMapping("/admin/category/{id}")
	public Object deleteCategory(@PathVariable("id") Serializable id) {
		if (categoryService.removeWithChildren(id)) {
			return new JsonResponse(HttpUtils.Status_OK, "删除目录成功！");
		} else {
			log.error("删除目录操作异常！");
			return new JsonResponse(HttpUtils.Status_BadRequest, "删除目录失败！");
		}
	}
}