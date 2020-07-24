package com.frankfang.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.frankfang.entity.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class CategoryWithChildren extends Category {

	/**
	 * 实现序列化接口
	 */
	private static final long serialVersionUID = 1L;
		
	private List<Category> children;

}
