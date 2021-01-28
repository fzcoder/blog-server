package com.fzcoder.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fzcoder.bean.ArticleDownloadConfigInfo;
import com.fzcoder.dto.ArticleForm;
import com.fzcoder.service.*;
import com.fzcoder.utils.ConstUtils;
import com.fzcoder.vo.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fzcoder.entity.Article;
import com.fzcoder.bean.JsonResponse;
import com.fzcoder.utils.HttpUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class ArticleController {

	@Autowired
	private ArticleService service;

	@PostMapping("/admin/article")
	public Object addArticle(@RequestBody ArticleForm form) {
		// 插入数据
		if (service.save(form)) {
			return new JsonResponse(HttpServletResponse.SC_OK, "添加文章成功！");
		} else {
			return new JsonResponse(HttpServletResponse.SC_BAD_REQUEST, "添加文章失败！");
		}
	}

	@GetMapping("/admin/article/view")
	public Object getOne(@RequestParam("aid") Long id) {
		// 1.获取文章视图
		Post post = service.getViewById(id);
		// 2.判断视图是否为空
		if (post == null) {
			return new JsonResponse(HttpServletResponse.SC_NOT_FOUND,
					"Error: Request failed with status code 404");
		} else {
			return new JsonResponse(post);
		}
	}

	@GetMapping("/admin/article/form")
	public Object getArticle(@RequestParam("aid") Long id) {
		// 1.获取文章表单
		ArticleForm form = service.getFormById(id);
		// 2.判断文章表单是否为空
		if (form == null) {
			return new JsonResponse(HttpServletResponse.SC_NOT_FOUND,
					"Error: Request failed with status code 404");
		} else {
			return new JsonResponse(form);
		}
	}

	@GetMapping("/admin/article")
	public Object getPages(@RequestParam("uid") Integer uid,
						   @RequestParam("key") String key,
						   @RequestParam("page_num") long pageNum,
						   @RequestParam("page_size") long pageSize,
						   @RequestParam("status") int status,
						   @RequestParam Map<String, Object> params) {
		// 判断请求是否正确
		if (status == ConstUtils.ARTICLE_STATUS_DRAFT ||
				status == ConstUtils.ARTICLE_STATUS_PUBLISHED ||
				status == ConstUtils.ARTICLE_STATUS_REMOVED) {
			return new JsonResponse(service.getPages(uid, key, pageNum, pageSize, status, params));
		} else {
			return new JsonResponse(HttpServletResponse.SC_BAD_REQUEST, "请求出错！");
		}
	}

	@PostMapping("/admin/article/upload")
	public void upload(HttpServletRequest request, HttpServletResponse response) {
		service.upload(request, response);
	}

	@PostMapping("/admin/article/{id}/download")
	public void download(@PathVariable("id") Long id, HttpServletResponse response,
						 @RequestBody ArticleDownloadConfigInfo info) {
		service.download(id, response, info);
	}

	@PutMapping("/admin/article")
	public Object setArticle(@RequestBody ArticleForm form) {
		if (service.update(form)) {
			return new JsonResponse(HttpServletResponse.SC_OK, "文章修改成功！");
		} else {
			return new JsonResponse(HttpServletResponse.SC_BAD_REQUEST, "文章修改失败！");
		}
	}

	@PatchMapping("/admin/article/{id}")
	public Object updateArticleInfo(@PathVariable("id") Long id, @RequestBody Map<String, Object> map) {
		if (map.containsKey("op") && map.containsKey("path") && map.containsKey("value")) {
			UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
			updateWrapper.eq("id", id);
			switch (map.get("path").toString())
			{
				case "/status":
					if (map.get("op").toString().equals("replace")) {
						updateWrapper.set("status", map.get("value"));
					}
					break;
				default:
					return new JsonResponse(HttpServletResponse.SC_BAD_REQUEST, "请求错误!");
			}
			if (service.update(updateWrapper)) {
				return new JsonResponse(HttpServletResponse.SC_OK, "更新成功!");
			} else {
				return new JsonResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "更新失败!");
			}
		} else {
			return new JsonResponse(HttpServletResponse.SC_BAD_REQUEST, "请求错误！");
		}
	}

	@DeleteMapping("/admin/article/{id}")
	public Object deleteArticle(@PathVariable("id") Long id) {
		if (service.removeById(id)) {
			return new JsonResponse(HttpUtils.Status_OK, "文章删除成功！");
		} else {
			return new JsonResponse(HttpUtils.Status_BadRequest, "文章删除失败！");
		}
	}
}
