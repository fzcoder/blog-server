package com.frankfang.service;

import com.aliyun.oss.model.DeleteObjectsResult;
import com.frankfang.bean.OssCallBackResult;
import com.frankfang.bean.OssPolicyResult;

import java.util.List;
import java.util.Map;

public interface OssService {

	OssPolicyResult policy(String type);

	OssCallBackResult callback(Map<String, Object> requestBody);

	DeleteObjectsResult deleteObjects(List<String> urlList);
}
