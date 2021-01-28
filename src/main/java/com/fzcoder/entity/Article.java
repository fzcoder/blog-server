package com.fzcoder.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 
 * @author Frank Fang
 * @description 文章的实体类
 * 
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_article")
public class Article implements Serializable {

	/**
	 * 实现序列化接口
	 */
    @TableId("id")
	private Long id;

    @TableField("author_id")
	private Integer authorId;

    @TableField("title")
	private String title;

    @TableField("date")
	private String date;

    @TableField("update_time")
	private String updateTime;

    @TableField("introduction")
	private String introduction;

    @TableField("category_id")
	private String categoryId;

    @TableField("tags")
	private String tags;

	@TableField("cover")
	private String cover;

    @TableField("content_md")
	private String contentMd;

    @TableField("content_html")
	private String contentHtml;

	@TableField("status")
	private int status;
}
