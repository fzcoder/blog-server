package com.fzcoder.opensource.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryForm {
    // 目录id
    private String categoryId;
    // 上一级id
    private String parentId;
    // 目录名称
    private String categoryName;
    // 目录等级
    private int level;
    // 描述
    private String description;
    // 目录图标
    private String icon;
    // 目录类型
    private String type;
}
