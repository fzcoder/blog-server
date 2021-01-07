package com.fzcoder.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fzcoder.service.RedisService;

import javax.annotation.Resource;

@Service
public class RedisServiceImpl implements RedisService {
	
	@Resource(name = "redisTemplate")
	private RedisTemplate<Object, Object> redisTemplate;

	@Override
	public boolean set(Object key, Object value) {
		try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Object get(Object key) {
		try {
			return redisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean delete(Object key) {
		try {
			redisTemplate.delete(key);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean expire(Object key, long time) {
		try {
			if (time > 0) {
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public long getExpire(Object key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

}
