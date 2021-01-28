package com.fzcoder.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

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
public class Category implements Serializable {
	
	/**
	 * 实现序列化接口
	 */
	private static final long serialVersionUID = 1L;

    @TableId(value = "id")
	private String id;

    @TableField("parent_id")
	private String parentId;

    @TableField("name")
	private String name;

    @TableField("level")
	private int level;

    @TableField("description")
	private String description;

    @TableField("icon")
	private String icon;

    @TableField("type")
	private String type;
}
