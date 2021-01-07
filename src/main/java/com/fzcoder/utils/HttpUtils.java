package com.fzcoder.utils;

/**
 * @author: Frank Fang
 * @date: 2020-02-01
 * @Description: HTTP相关工具类
 */
public class HttpUtils {
	
	/**
	 * @author: Frank Fang
	 * @Description: 返回的状态码为200，表示成功
	 */
	public static final Integer Status_OK = 200;
	
	/**
	 * @author: Frank Fang
	 * @Description: 返回的状态码为201，表示创建成功
	 */
	public static final Integer Status_Created = 201;
	
	/**
	 * @author: Frank Fang
	 * @Description: 返回的状态码为400，表示错误请求
	 */
	public static final Integer Status_BadRequest = 400;
	
	/**
	 * @author: Frank Fang
	 * @Description: 返回的状态码为401，表示无访问权限
	 */
	public static final Integer Status_UnAuthorized = 401;
	
	/**
	 * @author: Frank Fang
	 * @Description: 返回的状态码为403，表示禁止访问资源
	 */
	public static final Integer Status_Forbidden = 403;
	
	/**
	 * @author: Frank Fang
	 * @Description: 返回的状态码为404，表示资源或页面不存在
	 */
	public static final Integer Status_NotFound = 404;
	
	/**
	 * @author: Frank Fang
	 * @Description: 返回的状态码为500，表示服务器端发生错误
	 */
	public static final Integer Status_InternalServerError = 500;
	
	/**
	 * @author: Frank Fang
	 * @Description: 返回的状态码为502，表示代理出错
	 */
	public static final Integer Status_BadGateway = 502;
}
