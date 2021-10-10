package com.fzcoder.opensource.blog.bean;

import com.fzcoder.opensource.blog.utils.HttpUtils;

public class JsonResponse {
	
	// 状态码
	private Integer status;
	
	// 信息
	private String message;
	
	// 数据
	private Object data;
	
	
	// 默认构造方法
	public JsonResponse() {
		
	}
	
	// 自定义构造方法
	public JsonResponse(Integer status, String message, Object data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}
	
	public JsonResponse(Integer status, String message) {
		this.status = status;
		this.message = message;
		this.data = null;
	}
	
	public JsonResponse(String message, Object data) {
		this.setStatus(HttpUtils.Status_OK);
		this.setMessage(message);
		this.setData(data);
	}
	
	/**
	 * 返回数据(status:200, message:null, data:data)
	 * @param data
	 */
	public JsonResponse(Object data) {
		this.setStatus(HttpUtils.Status_OK);
		this.setMessage(null);
		this.setData(data);
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	} 	
}
