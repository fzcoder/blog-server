package com.fzcoder.opensource.blog.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryView implements Serializable {

    private static final long serialVersionUID = 1L;

    private String categoryId;

    private String categoryName;
}
