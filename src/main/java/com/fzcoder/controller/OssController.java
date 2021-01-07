package com.fzcoder.controller;

import com.fzcoder.bean.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fzcoder.service.OssService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Api(tags = "阿里云对象存储接口")
@RequestMapping("/api")
@RestController
public class OssController {
	
	@Autowired
	private OssService ossService;
	
	@ApiOperation(value = "OSS上传签名生成")
	@GetMapping("/admin/aliyun/oss/policy")
	public Object policy(@RequestParam("type") String type) {
		return ossService.policy(type);
	}

	@ApiOperation(value = "OSS上传成功回调")
	@PostMapping("/aliyun/oss/callback")
	public Object callback(@RequestBody Map<String, Object> requestBody) {
		return ossService.callback(requestBody);
	}

	/**
	 * 删除单个文件
	 * @param url - 要删除文件的地址，如：http://pic.xxx.com/xxxxxx.jpg
	 * @param type - 要删除文件的类型，如：img代表图片类型，video：代表视频类型
	 * @return
	 */
	@ApiOperation(value = "删除文件")
	@PutMapping("/admin/aliyun/oss/delete")
	public Object deleteObject(@RequestBody List<String> urlList) {
		// List<String> urlList = new ArrayList<>();
		// urlList.add(url);
		if (ossService.deleteObjects(urlList).getDeletedObjects().isEmpty()) {
			return new JsonResponse(HttpServletResponse.SC_OK, "删除成功！");
		} else {
			return new JsonResponse(HttpServletResponse.SC_BAD_REQUEST, "删除失败！");
		}
	}
}
