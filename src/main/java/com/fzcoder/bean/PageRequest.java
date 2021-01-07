package com.fzcoder.bean;

import lombok.Data;

@Data
public class PageRequest {

	private String key;
	
	private long pageNum;
	
	private long pageSize;
	
	private String orderBy[];
	
	private boolean reverse;
}
