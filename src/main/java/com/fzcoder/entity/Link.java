package com.fzcoder.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("tb_link")
public class Link implements Serializable {

	/**
	 * 实现序列化接口
	 */
	private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
	private Integer id;

    @TableField("name")
	private String name;

    @TableField("linkUrl")
	private String linkUrl;

    @TableField("iconUrl")
	private String iconUrl;

    @TableField("shortcut")
	private Boolean shortcut;

    @TableField("category_id")
	private Integer categoryId;

    @TableField("category_name")
	private String categoryName;
	
	public Link(Map<String, ?> map) {
		if(map.containsKey("id") && map.get("id") != null) {
			this.setId((Integer) map.get("id"));
		}
		this.setName((String) map.get("name"));
		this.setLinkUrl((String) map.get("linkUrl"));
		this.setIconUrl((String) map.get("iconUrl"));
	}
	
	public Link(Integer id, Map<String, ?> map) {
		this.setId(id);
		this.setName((String) map.get("name"));
		this.setLinkUrl((String) map.get("linkUrl"));
		this.setIconUrl((String) map.get("iconUrl"));
	}
	
	public Link(Map<String, ?> map, Boolean isFast) {
		this.setName((String) map.get("name"));
		this.setLinkUrl((String) map.get("linkUrl"));
		this.setIconUrl((String) map.get("iconUrl"));
		this.setShortcut(isFast);
	}
	
	public Link(Integer id, Map<String, ?> map, Boolean isFast) {
		this.setId(id);
		this.setName((String) map.get("name"));
		this.setLinkUrl((String) map.get("linkUrl"));
		this.setIconUrl((String) map.get("iconUrl"));
		this.setShortcut(isFast);
	}
	
	public Map<String, Object> toMap(){
		Map<String, Object> map = new HashMap<>();
		map.put("id", this.getId());
		map.put("name", this.getName());
		map.put("linkUrl", this.getLinkUrl());
		map.put("iconUrl", this.getIconUrl());
		map.put("visible", this.getShortcut());
		return map;
	}
}
