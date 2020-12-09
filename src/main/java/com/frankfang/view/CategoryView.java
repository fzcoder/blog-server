package com.frankfang.view;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryView implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer categoryId;

    private String categoryName;
}
