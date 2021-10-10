package com.fzcoder.opensource.blog.service;

import com.aliyun.oss.model.DeleteObjectsResult;
import com.fzcoder.opensource.blog.utils.oss.OssCallBackResult;
import com.fzcoder.opensource.blog.utils.oss.OssPolicyResult;

import java.util.List;
import java.util.Map;

public interface OssService {

	OssPolicyResult policy(String type);

	OssCallBackResult callback(Map<String, Object> requestBody);

	DeleteObjectsResult deleteObjects(List<String> urlList);
}
