package com.fzcoder.opensource.blog.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.*;

import com.aliyun.oss.internal.OSSUtils;
import com.aliyun.oss.model.*;
import com.fzcoder.opensource.blog.utils.oss.OssCallBackResult;
import com.fzcoder.opensource.blog.config.autoconfigure.OssConfigProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.fzcoder.opensource.blog.service.OssService;
import com.fzcoder.opensource.blog.utils.oss.OssPolicyResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@PropertySource(value = {"classpath:application.properties"}, encoding = "utf-8")
public class OssServiceImpl implements OssService {
	private String ENDPOINT;
	private String ACCESS_KEY_ID;
	private String BUCKET_NAME;
	private int EXPIRE;
	private int MAX_SIZE;
	private String DIR_PREFIX;
	private String CALLBACK;
	private String BIND_DOMAIN_NAME;

	private OSS ossClient;

	public OssServiceImpl(OssConfigProperties ossConfigProperties, OSS ossClient) {
		this.ossClient = ossClient;
		ENDPOINT = ossConfigProperties.getEndpoint();
		ACCESS_KEY_ID = ossConfigProperties.getAccessKeyId();
		BUCKET_NAME = ossConfigProperties.getBucketName();
		EXPIRE = Integer.parseInt(ossConfigProperties.getPolicy().get("expire"));
		MAX_SIZE = ossConfigProperties.getMaxSize();
		DIR_PREFIX = ossConfigProperties.getDir().get("prefix");
		CALLBACK = ossConfigProperties.getCallback();
		BIND_DOMAIN_NAME = ossConfigProperties.getBindDomainName();
	}

	@Override
	public OssPolicyResult policy(String type) {
		OssPolicyResult result = new OssPolicyResult();
		String dir;
		if (!DIR_PREFIX.equals("")) {
			dir = DIR_PREFIX + "/" + type;
		} else {
			dir = type;
		}
		// 签名有效期
		long expireEndTime = System.currentTimeMillis() + EXPIRE * 1000;
		Date expiration = new Date(expireEndTime);
		// 文件名称
		String filename = UUID.randomUUID().toString();
		// 文件大小
		long maxSize = MAX_SIZE * 1024 * 1024;
		// 提交节点
		String action = "https://" + BUCKET_NAME + "." + ENDPOINT;
		// 上传回调参数
		Callback callback = new Callback();
		// 指定请求回调的服务器URL
		callback.setCallbackUrl(CALLBACK);
		//（可选）设置回调请求消息头中Host的值，即您的服务器配置Host的值。
		// callback.setCallbackHost("yourCallbackHost");
		// 设置发起回调时请求body的值。
		callback.setCallbackBody("{\\\"filename\\\":${object}}");
		// 设置发起回调请求的Content-Type。
		callback.setCalbackBodyType(Callback.CalbackBodyType.JSON);
		// 设置发起回调请求的自定义参数，由Key和Value组成，Key必须以x:开始。
		// callback.addCallbackVar("x:dir", "value");
		try {
			PolicyConditions policyConds = new PolicyConditions();
			policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSize);
			policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
			String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
			byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
			String policy = BinaryUtil.toBase64String(binaryData);
			String signature = ossClient.calculatePostSignature(postPolicy);
			String callbackData = BinaryUtil.toBase64String(OSSUtils.jsonizeCallback(callback).getBytes());
			// 返回结果
			result.setAccessKeyId(ACCESS_KEY_ID);
			result.setPolicy(policy);
			result.setSignature(signature);
			result.setKey(filename);
			result.setDir(dir);
			result.setHost(action);
			result.setCallback(callbackData);
		} catch (Exception e) {
			log.error("签名生成失败", e);
		}
		return result;
	}

	@Override
	public OssCallBackResult callback(Map<String, Object> requestBody) {
		// System.out.println(requestBody.toString());
		OssCallBackResult ossCallbackResult = new OssCallBackResult();
		// 文件名
		String filename = requestBody.get("filename").toString();
		// 文件链接
		// String url = "https://" + BUCKET_NAME + "." + ENDPOINT + "/" + filename;
		String url = BIND_DOMAIN_NAME + filename;
		ossCallbackResult.setUrl(url);
		return ossCallbackResult;
	}

	@Override
	public DeleteObjectsResult deleteObjects(List<String> urlList) {
		List<String> keys = new LinkedList<>();
		for (String url : urlList) {
			String key = url.substring(BIND_DOMAIN_NAME.length());
			keys.add(key);
		}
		System.out.println(keys.toString());
		return ossClient.deleteObjects(new DeleteObjectsRequest(BUCKET_NAME).withKeys(keys).withQuiet(true));
	}

}
