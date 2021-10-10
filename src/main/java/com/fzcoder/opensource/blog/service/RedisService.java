package com.fzcoder.opensource.blog.service;

public interface RedisService {

	/**
	 * @description 添加缓存
	 * @param key
	 * @param value
	 * @return
	 */
	boolean set(Object key, Object value);
	
	/**
	 * @description 获取缓存
	 * @param key
	 * @return
	 */
	Object get(Object key);
	
	/**
	 * @description 删除键值对
	 * @param key
	 * @return
	 */
	boolean delete(Object key);
	
	/**
	 * @description 指定缓存失效时间
	 * @param key  键
	 * @param time 时间(秒)
	 * @return
	 */
	boolean expire(Object key, long time);
	
	/**
	 * @description 根据key获取过期时间
	 * @param key
	 * @return
	 */
	long getExpire(Object key);
}
