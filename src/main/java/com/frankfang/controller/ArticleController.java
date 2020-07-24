package com.frankfang.controller;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.frankfang.entity.Like;
import com.frankfang.entity.record.ArticleRecord;
import com.frankfang.service.ArticleRecordService;
import com.frankfang.service.LikeService;
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
import com.frankfang.entity.Article;
import com.frankfang.bean.PageRequest;
import com.frankfang.bean.JsonResponse;
import com.frankfang.service.ArticleService;
import com.frankfang.service.CategoryService;
import com.frankfang.utils.HttpUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Slf4j
@Api(tags = "文章模块接口")
@RestController
@RequestMapping("/api")
public class ArticleController {

	@Autowired
	private ArticleService service;
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private LikeService likeService;

	@Autowired
	private ArticleRecordService articleRecordService;

	// 查询字段
	private static final String[] SQLSELECT_LIST = { "id", "author_id", "title", "date", "update_time", "category_name",
			"tags", "introduction", "cover" };

	@ApiOperation(value = "添加文章")
	@PostMapping("/admin/article")
	public Object addArticle(@RequestBody Article article) {
		// 获取文章创建时间
		Date date = new Date();
		// 对时间进行格式化
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 设置时区为东8区(北京时间)
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		// 设置时间
		article.setDate(sdf.format(date));
		article.setUpdateTime(sdf.format(date));
		// 设置目录名称
		article.setCategoryName(categoryService.getById(article.getCategoryId()).getName());
		if (service.save(article)) {
			return new JsonResponse(HttpUtils.Status_OK, "添加文章成功！");
		} else {
			log.error("添加文章操作异常！");
			return new JsonResponse(HttpUtils.Status_BadRequest, "添加文章失败！");
		}
	}

	@ApiOperation(value = "获取文章信息")
	@GetMapping("/article/{id}")
	public Object getOne(@PathVariable("id") Integer id) {
		QueryWrapper<Article> wrapper = new QueryWrapper<>();
		wrapper.eq("id", id);
		Map<String, Object> article = service.getMap(wrapper);
		if (article == null) {
			return new JsonResponse(HttpServletResponse.SC_NOT_FOUND, "Error: Request failed with status code 404");
		} else {
			article.remove("content_md");
			String tags = article.get("tags").toString();
			article.replace("tags", tags.split(","));
			return new JsonResponse(article);
		}
	}
	
	@ApiOperation(value = "获取文章信息(admin)")
	@GetMapping("/admin/article/{id}")
	public Object getArticle(@PathVariable("id") Integer id) {
		return new JsonResponse(service.getById(id));
	}

	@ApiOperation(value = "获取文章列表")
	@PostMapping("/article")
	public Object getList(@RequestParam Map<String, Object> params, @RequestBody PageRequest pageRequest) {
		// 1. 生成条件构造器
		QueryWrapper<Article> wrapper = new QueryWrapper<>();
		if (params.containsKey("tag")) {
			wrapper.like(true, "tags", params.get("tag").toString());
			params.remove("tag");
		}
		wrapper.allEq(true, params, false);
		wrapper.like(true, "title", pageRequest.getKey());
		wrapper.select(SQLSELECT_LIST);
		wrapper.orderBy(true, !pageRequest.isReverse(), pageRequest.getOrderBy());
		// 2. 分页查询
		IPage<Map<String, Object>> page = service
				.pageMaps(new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize()), wrapper);
		// 3. 数据封装
		for (Map<String, Object> item : page.getRecords()) {
			String tags = item.get("tags").toString();
			item.replace("tags", tags.split(","));
			QueryWrapper<Like> likeQueryWrapper = new QueryWrapper<>();
			QueryWrapper<ArticleRecord> articleRecordQueryWrapper = new QueryWrapper<>();
			Map<String, Object> map = new HashMap<>();
			map.put("object_name", "article");
			map.put("object_id", item.get("id"));
			likeQueryWrapper.allEq(true, map, false);
			item.put("like", likeService.count(likeQueryWrapper));
			map.clear();
			map.put("article_id", item.get("id"));
			articleRecordQueryWrapper.allEq(true, map, false);
			item.put("view", articleRecordService.count(articleRecordQueryWrapper));
		}
		// 4. 返回结果
		return new JsonResponse(page);
	}

	@ApiOperation(value = "上传文章")
	@PostMapping("/admin/article/upload")
	public Object upload(HttpServletRequest request) {
		// MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
		List<MultipartFile> multipartFiles = ((MultipartHttpServletRequest) request)
				.getFiles("file");
		/* String name=params.getParameter("name");
		System.out.println("name:"+name);
		String id=params.getParameter("id");
		System.out.println("id:"+id); */
		MultipartFile multipartFile = null;
		BufferedOutputStream stream;
		for (MultipartFile MF : multipartFiles) {
			multipartFile = MF;
			if (!multipartFile.isEmpty()) {
				try {
					byte[] bytes = multipartFile.getBytes();
					stream = new BufferedOutputStream(new FileOutputStream(
							new File(multipartFile.getOriginalFilename())));
					stream.write(bytes);
					stream.close();
				} catch (Exception e) {
					return new JsonResponse(HttpServletResponse.SC_BAD_REQUEST, "文件上传失败!");
				}
			} else {
				return new JsonResponse(HttpServletResponse.SC_BAD_REQUEST, "文件上传内容不能为空!");
			}
		}
		// 读取文件内容
		try {
			File file = new File(multipartFile.getOriginalFilename());
			FileInputStream inputStream = new FileInputStream(file);
			byte[] b = new byte[inputStream.available()];
			inputStream.read(b);
			inputStream.close();
			file.delete();
			return new JsonResponse("文件上传成功!", new String(b));
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResponse(HttpServletResponse.SC_BAD_REQUEST, "文件读取失败!");
		}
	}
	
	@ApiOperation(value = "下载文章")
	@PostMapping("/admin/article/{id}/download")
	public Object download(@PathVariable("id") Integer id, HttpServletResponse response) {
		/*
		 * // 获取文章创建时间 Date date = new Date(); // 设置时间格式 SimpleDateFormat sdf_file = new
		 * SimpleDateFormat("yyyy/MM/dd"); SimpleDateFormat sdf_data = new
		 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 对时间进行格式化 String format_data =
		 * sdf_data.format(date); String format_file = sdf_file.format(date); //
		 * 获取项目的根路径 File path = new File(ResourceUtils.getURL("classpath:").getPath());
		 * // 构建文件存放路径 File folder = new File(path.getAbsolutePath() +
		 * "/static/upload/markdown/" + format_file); if (!folder.isDirectory()) {
		 * folder.mkdirs(); }
		 */
		// 利用UUID生成下载文件名
		String fileName = UUID.randomUUID().toString() + ".md";
		// 创建文件
		File file = new File(fileName);
		try {
			log.info("正在生成文件...");
			// 创建输出流
			FileOutputStream os = new FileOutputStream(file, false);
			OutputStreamWriter writer = new OutputStreamWriter(os, StandardCharsets.UTF_8);
			// 写入文件
			writer.append(service.getById(id).getContentMd());
			// 关闭文件
			writer.close();
		} catch (Exception e) {
			log.error("文件写入发生异常！");
			e.printStackTrace();
			return new JsonResponse(HttpUtils.Status_InternalServerError, "下载发生错误！");
		}
		// 设置下载文件格式
		response.setContentType("application/octet-stream");
		// 设置文件名
		response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		try {
			log.info("正在进行文件流传输...");
			// 实现文件下载
			byte[] buffer = new byte[1024];
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			OutputStream os = response.getOutputStream();
			int i = bis.read(buffer);
			while (i != -1) {
				os.write(buffer, 0, i);
				i = bis.read(buffer);
			}
			bis.close();
			log.info("文件传输完成！");
		} catch (Exception e) {
			log.error("文件传输出现异常！");
			e.printStackTrace();
			return new JsonResponse(HttpUtils.Status_InternalServerError, "下载发生错误！");
		}
		// 删除文件
		if (!file.delete()) {
			log.warn("文件删除失败！");
		}
		// 返回数据
		return new JsonResponse(HttpUtils.Status_OK, "文章下载成功！");
	}

	@ApiOperation(value = "修改文章")
	@PutMapping("/admin/article")
	public Object setArticle(@RequestBody Article article) {
		// 获取文章创建时间
		Date date = new Date();
		// 对时间进行格式化
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 设置时区为东8区(北京时间)
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		// 设置时间
		article.setUpdateTime(sdf.format(date));
		if (service.updateById(article)) {
			return new JsonResponse(HttpUtils.Status_OK, "文章修改成功！");
		} else {
			log.error("修改文章出现异常！");
			return new JsonResponse(HttpUtils.Status_BadRequest, "文章修改失败！");
		}
	}

	@ApiOperation(value = "删除文章")
	@DeleteMapping("/admin/article/{id}")
	public Object deleteArticle(@PathVariable("id") Integer id) {
		if (service.removeById(id)) {
			return new JsonResponse(HttpUtils.Status_OK, "文章删除成功！");
		} else {
			log.error("删除文章出现异常！");
			return new JsonResponse(HttpUtils.Status_BadRequest, "文章删除失败！");
		}
	}
}
