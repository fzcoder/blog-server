package com.fzcoder.opensource.blog.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ArticleView implements Serializable {

    /**
     * 实现序列化接口
     */
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    @TableField("author")
    private UserView author;

    @TableField("title")
    private String title;

    @TableField("createTime")
    private String createTime;

    @TableField("updateTime")
    private String updateTime;

    @TableField("introduction")
    private String introduction;

    @TableField("category")
    private CategoryView category;

    private List<TagView> tags;

    @TableField("cover")
    private String cover;
}
