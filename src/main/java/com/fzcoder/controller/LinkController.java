package com.fzcoder.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzcoder.entity.Category;
import com.fzcoder.entity.Link;
import com.fzcoder.bean.PageRequest;
import com.fzcoder.bean.JsonResponse;
import com.fzcoder.service.CategoryService;
import com.fzcoder.service.LinkService;
import com.fzcoder.utils.HttpUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class LinkController {

	@Autowired
	private LinkService service;
	
	@Autowired
	private CategoryService categoryService;

	// 查询字段
	private static final String[] SQLSELECT_LIST = { "id", "name", "linkUrl", "iconUrl", "shortcut", "category_id",
			"category_name" };

	@PostMapping("/admin/link")
	public Object addLink(@RequestBody Link link) {
		link.setCategoryName(categoryService.getById(link.getCategoryId()).getName());
		if (service.save(link)) {
			return new JsonResponse(HttpUtils.Status_OK, "链接创建成功！");
		} else {
			log.error("添加链接发生异常！");
			return new JsonResponse(HttpUtils.Status_BadRequest, "链接创建失败！");
		}
	}

	@GetMapping("/link/{id}")
	public Object getLink(@PathVariable("id") Integer id) {
		return new JsonResponse(service.getById(id));
	}

	@GetMapping("/link")
	public Object getList(@RequestParam Map<String, Object> params) {
		if (params.containsKey("shortcut")) {
			QueryWrapper<Link> wrapper = new QueryWrapper<>();
			if (params.get("shortcut").toString().equals("true")) {
				params.replace("shortcut", 1);
			}
			if (params.get("shortcut").toString().equals("false")) {
				params.replace("shortcut", 0);
			}
			wrapper.allEq(true, params, false);
			return new JsonResponse(service.listMaps(wrapper));
		} else if (params.containsKey("group")) {
			if (params.get("group").toString().equals("true")) {
				List<Map<String, Object>> list = new LinkedList<>();
				for (Map<String, Object> item : service.getCategoryGroup()) {
					QueryWrapper<Link> wrapper = new QueryWrapper<>();
					String[] sqlSelect = {"id", "name", "linkUrl", "iconUrl"};
					wrapper.allEq(true, item, false);
					wrapper.select(sqlSelect);
					Map<String, Object> elem = new HashMap<>(item);
					elem.put("links", service.listMaps(wrapper));
					list.add(elem);
				}
				return new JsonResponse(list);
			} else {
				return new JsonResponse(service.list());
			}
		} else {
			return new JsonResponse(null);
		}
	}

	@PostMapping("/link")
	public Object getPage(@RequestParam Map<String, Object> params, @RequestBody PageRequest pageRequest) {
		// 1. 生成条件构造器
		QueryWrapper<Link> wrapper = new QueryWrapper<>();
		wrapper.allEq(true, params, false);
		wrapper.like(true, "name", pageRequest.getKey());
		wrapper.select(SQLSELECT_LIST);
		wrapper.orderBy(true, !pageRequest.isReverse(), pageRequest.getOrderBy());
		// 2. 分页查询
		IPage<Map<String, Object>> page = service
				.pageMaps(new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize()), wrapper);
		// 3. 返回结果
		return new JsonResponse(page);
	}

	@PutMapping("/admin/link/{id}")
	public Object updateLink(@PathVariable("id") Integer id, @RequestParam Map<String, Object> params) {
		UpdateWrapper<Link> wrapper = new UpdateWrapper<>();
		String str = "";
		// 修改是否为快速链接
		if (params.containsKey("shortcut")) {
			// 判断真值
			if (params.get("shortcut").toString().equals("true")) {
				str = str.concat("shortcut=1").concat(", ");
			} else {
				str = str.concat("shortcut=0").concat(", ");
			}
		}
		// 修改所属目录
		if (params.containsKey("category_id")) {
			// 通过条件构造器查询目录名称，这样可以避免ClassCastException
			QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq(true, "id", params.get("category_id"));
			String categoryName = categoryService.getOne(queryWrapper).getName();
			// SQL语句拼写
			str = str.concat("category_id="  + params.get("category_id").toString()).concat(", ");
			str = str.concat("category_name="  + "'" + categoryName  + "'").concat(", ");
		}
		String sqlSet = str.substring(0, str.length() - 2);
		wrapper.setSql(true, sqlSet);
		wrapper.eq(true, "id", id);
		if (service.update(wrapper)) {
			return new JsonResponse(HttpUtils.Status_OK, "修改成功！");
		} else {
			log.error("链接修改操作(路径参数)发生异常！");
			log.error("本次操作的SQL语句为：" + sqlSet);
			return new JsonResponse(HttpUtils.Status_BadRequest, "修改失败！");
		}
	}

	@PutMapping("/admin/link")
	public Object updateLink(@RequestBody Link link) {
		if (service.updateById(link)) {
			return new JsonResponse(HttpUtils.Status_OK, "修改成功！");
		} else {
			log.error("链接修改操作(请求体)发生异常！");
			return new JsonResponse(HttpUtils.Status_BadRequest, "修改失败！");
		}
	}

	@DeleteMapping("/admin/link/{id}")
	public Object deleteLinkById(@PathVariable("id") Integer id) {
		if (service.removeById(id)) {
			return new JsonResponse(HttpUtils.Status_OK, "链接删除成功！");
		} else {
			log.error("链接删除操作发生异常！");
			return new JsonResponse(HttpUtils.Status_BadRequest, "链接删除失败！");
		}
	}
}
