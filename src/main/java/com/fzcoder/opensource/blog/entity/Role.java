package com.fzcoder.opensource.blog.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Role implements Serializable {
	
	/**
	 * 实现序列化接口
	 */
	private static final long serialVersionUID = -7398193545817241819L;
	
	private Integer id;
	
	private String name;
	
	private String name_zh;
}
