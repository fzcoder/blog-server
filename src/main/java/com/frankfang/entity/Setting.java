package com.frankfang.entity;

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

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_setting")
@ApiModel(value="link对象", description="")
public class Setting implements Serializable {

	@ApiModelProperty(value = "id，作为唯一的表示")
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "网站名称")
	@TableField("brand")
	private String brand;

	@ApiModelProperty(value = "版权信息")
	@TableField("copyright")
	private String copyright;

	@ApiModelProperty(value = "备案信息")
	@TableField("beianInfo")
	private String beianInfo;

	@ApiModelProperty(value = "声明信息")
	@TableField("statement")
	private String statement;
}
