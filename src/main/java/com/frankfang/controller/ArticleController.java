package com.frankfang.controller;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.frankfang.aspect.RequestLimit;
import com.frankfang.entity.Like;
import com.frankfang.entity.record.ArticleRecord;
import com.frankfang.service.*;
import com.frankfang.utils.ConstUtils;
import com.frankfang.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.frankfang.entity.Article;
import com.frankfang.bean.PageRequest;
import com.frankfang.bean.JsonResponse;
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
	private EventService eventService;

	@Autowired
	private ArticleRecordService articleRecordService;

	// 查询字段
	private static final String[] SQLSELECT_LIST = { "id", "author_id", "title", "date", "update_time", "category_id",
			"tags", "introduction", "cover" };

	/**
	 * 安全的id生成器，确保id在数据库中的唯一性
	 * @param date 规格化日期
	 * @return id 生成的Id
	 */
	private Long safeIdGenerator(String date) {
		// 最大容量
		int max = 1000;
		// id统计器
		Set<Long> set = new HashSet<>();
		// 正则表达式
		String regex = "-";
		// id主键
		Long id;
		// 生成条件构造器
		QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
		// 判断生成的id是否重复并采取措施确保id全库唯一
		do {
			// 开始随机生成
			id = IdGenerator.createIdByDate(date, regex, max);
			// 将id存入集合中
			set.add(id);
			// 设置查询条件
			queryWrapper.eq("id", id);
			// 当集合中的元素等于最大容量时将id位数扩大1位(利用集合的不可重复性)
			if (set.size() >= max) {
				set.clear();
				max *= 10;
			}
		} while (service.count(queryWrapper) != 0);
		return id;
	}

	// 对该接口的访问次数进行限制，每秒仅可访问一次
	@RequestLimit
	@ApiOperation(value = "添加文章")
	@PostMapping("/admin/article")
	public Object addArticle(@RequestBody Article article, HttpServletRequest request) {
		// 获取文章创建时间
		Date date = new Date();
		// 对时间进行格式化
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
		// 设置时区为东8区(北京时间)
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		sdf_date.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		// 设置时间
		article.setDate(sdf.format(date));
		article.setUpdateTime(sdf.format(date));
		// 生成文章id
		article.setId(safeIdGenerator(sdf_date.format(date)));
		// 插入数据
		if (service.save(article)) {
			// 添加事件
			if (!eventService.handleInsertArticleEvent(date, article, true)) {
				log.error("添加事件异常！");
			}
			return new JsonResponse(HttpServletResponse.SC_OK, "添加文章成功！");
		} else {
			log.error("添加文章操作异常！");
			// 添加事件
			if (!eventService.handleInsertArticleEvent(date, article, false)) {
				log.error("添加事件异常！");
			}
			return new JsonResponse(HttpServletResponse.SC_BAD_REQUEST, "添加文章失败！");
		}
	}

	@ApiOperation(value = "获取文章信息")
	@GetMapping("/article/{id}")
	public Object getOne(@PathVariable("id") Long id) {
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
	public Object getArticle(@PathVariable("id") Long id) {
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
			// 将标签转换为数组
			String tags = item.get("tags").toString();
			item.replace("tags", tags.split(","));
			// 获取点赞数
			QueryWrapper<Like> likeQueryWrapper = new QueryWrapper<>();
			QueryWrapper<ArticleRecord> articleRecordQueryWrapper = new QueryWrapper<>();
			Map<String, Object> map = new HashMap<>();
			map.put("object_name", "article");
			map.put("object_id", item.get("id"));
			likeQueryWrapper.allEq(true, map, false);
			item.put("like", likeService.count(likeQueryWrapper));
			map.clear();
			// 获取阅读量
			map.put("article_id", item.get("id"));
			articleRecordQueryWrapper.allEq(true, map, false);
			item.put("view", articleRecordService.count(articleRecordQueryWrapper));
			// 设置目录名称
			item.put("category_name", categoryService.getById(item.get("category_id").toString()).getName());
		}
		// 4. 返回结果
		return new JsonResponse(page);
	}

	@ApiOperation(value = "获取文章列表")
	@GetMapping("/article")
	public Object getList(@RequestParam("key") String key, @RequestParam("page_num") long pageNum,
						  @RequestParam("page_size") long pageSize,
						  @RequestParam("is_reverse") boolean isReverse,
						  @RequestParam("type") String type,
						  @RequestParam Map<String, Object> params){
		QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
		if (type.equals("category")) {
			// 按标题名称排序
			articleQueryWrapper.orderBy(true, !isReverse, "title");
		}
		if (type.equals("dynamic")) {
			// 按时间排序
			articleQueryWrapper.orderBy(true, !isReverse, "date");
		}
		if (params.containsKey("category_id")) {
			articleQueryWrapper.eq("category_id", params.get("category_id"));
		}
		articleQueryWrapper.eq("status", ConstUtils.ARTICLE_STATUS_PUBLISHED);
		articleQueryWrapper.like(true, "title", key);
		articleQueryWrapper.select(SQLSELECT_LIST);
		// 分页查询
		IPage<Map<String, Object>> page = service.pageMaps(new Page<>(pageNum, pageSize),articleQueryWrapper);
		// 数据封装
		for (Map<String, Object> item : page.getRecords()) {
			// 将标签转换为数组
			String tags = item.get("tags").toString();
			item.replace("tags", tags.split(","));
			// 获取点赞数
			QueryWrapper<Like> likeQueryWrapper = new QueryWrapper<>();
			QueryWrapper<ArticleRecord> articleRecordQueryWrapper = new QueryWrapper<>();
			Map<String, Object> map = new HashMap<>();
			map.put("object_name", "article");
			map.put("object_id", item.get("id"));
			likeQueryWrapper.allEq(true, map, false);
			item.put("like", likeService.count(likeQueryWrapper));
			map.clear();
			// 获取阅读量
			map.put("article_id", item.get("id"));
			articleRecordQueryWrapper.allEq(true, map, false);
			item.put("view", articleRecordService.count(articleRecordQueryWrapper));
			// 设置目录名称
			item.put("category_name", categoryService.getById(item.get("category_id").toString()).getName());
		}

		return new JsonResponse(page);
	}

	@ApiOperation(value = "获取文章列表")
	@GetMapping("/admin/article")
	public Object getPages(@RequestParam("uid") Integer uid,
						   @RequestParam("key") String key,
						   @RequestParam("pageNum") long pageNum,
						   @RequestParam("pageSize") long pageSize,
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
	public Object download(@PathVariable("id") Long id, HttpServletResponse response) {
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
		// 判断是否为草稿
		boolean isDraft = service.getById(article.getId()).getStatus() == ConstUtils.ARTICLE_STATUS_DRAFT;
		if (service.updateById(article)) {
			if (!eventService.handleUpdateArticleEvent(date, article, true, isDraft)) {
				log.error("添加事件异常！");
			}
			return new JsonResponse(HttpUtils.Status_OK, "文章修改成功！");
		} else {
			if (!eventService.handleUpdateArticleEvent(date, article, false, isDraft)) {
				log.error("添加事件异常！");
			}
			log.error("修改文章出现异常！");
			return new JsonResponse(HttpUtils.Status_BadRequest, "文章修改失败！");
		}
	}

	@ApiOperation(value = "修改文章局部信息")
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

	@ApiOperation(value = "删除文章")
	@DeleteMapping("/admin/article/{id}")
	public Object deleteArticle(@PathVariable("id") Long id) {
		if (service.removeById(id)) {
			if (!eventService.handleDeleteArticleEvent(new Date(), id, true)) {
				log.error("添加事件异常！");
			}
			return new JsonResponse(HttpUtils.Status_OK, "文章删除成功！");
		} else {
			if (!eventService.handleDeleteArticleEvent(new Date(), id, false)) {
				log.error("添加事件异常！");
			}
			log.error("删除文章出现异常！");
			return new JsonResponse(HttpUtils.Status_BadRequest, "文章删除失败！");
		}
	}
}
