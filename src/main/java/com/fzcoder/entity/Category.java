package com.fzcoder.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_category")
@ApiModel(value="Category对象", description="")
public class Category implements Serializable {
	
	/**
	 * 实现序列化接口
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id，作为唯一的表示")
    @TableId(value = "id")
	private String id;
	
	@ApiModelProperty(value = "上一级菜单id，为0则表示该菜单为根菜单")
    @TableField("parent_id")
	private String parentId;
	
	@ApiModelProperty(value = "目录名称")
    @TableField("name")
	private String name;
	
	@ApiModelProperty(value = "表示菜单的级别")
    @TableField("level")
	private int level;
	
	@ApiModelProperty(value = "目录的简介")
    @TableField("description")
	private String description;
	
	@ApiModelProperty(value = "目录图标的链接")
    @TableField("icon")
	private String icon;
	
	@ApiModelProperty(value = "类型，如果值为文章则表示属于文章的菜单")
    @TableField("type")
	private String type;
}
