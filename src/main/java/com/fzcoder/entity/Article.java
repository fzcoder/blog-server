package com.fzcoder.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="Article对象", description="")
public class Article implements Serializable {

	/**
	 * 实现序列化接口
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id，作为唯一的表示")
    @TableId("id")
	private Long id;
	
	@ApiModelProperty(value = "作者id")
    @TableField("author_id")
	private Integer authorId;

	@ApiModelProperty(value = "文章标题")
    @TableField("title")
	private String title;

	@ApiModelProperty(value = "创建日期")
    @TableField("date")
	private String date;
	
	@ApiModelProperty(value = "最后更新时间")
    @TableField("update_time")
	private String updateTime;

	@ApiModelProperty(value = "文章简介")
    @TableField("introduction")
	private String introduction;
	
	@ApiModelProperty(value = "目录id")
    @TableField("category_id")
	private Integer categoryId;
	
	@ApiModelProperty(value = "文章标签，以英文逗号分隔")
    @TableField("tags")
	private String tags;

	@ApiModelProperty(value = "封面链接")
	@TableField("cover")
	private String cover;

	@ApiModelProperty(value = "markdown格式内容")
    @TableField("content_md")
	private String contentMd;

	@ApiModelProperty(value = "html格式内容")
    @TableField("content_html")
	private String contentHtml;

	@ApiModelProperty(value = "文章状态，0表示为草稿，1表示已发布，2表示已删除")
	@TableField("status")
	private int status;
}
