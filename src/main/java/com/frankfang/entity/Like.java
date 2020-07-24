package com.frankfang.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author frank fang
 * @since 2020-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_like")
@ApiModel(value="Like对象", description="")
public class Like implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id，唯一标识")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "点赞时间")
    @TableField("date")
    private String date;

    @ApiModelProperty(value = "访问者id地址")
    @TableField("visitor_ip")
    private String visitorIp;

    @ApiModelProperty(value = "点赞对象")
    @TableField("object_name")
    private String objectName;

    @ApiModelProperty(value = "对象的id")
    @TableField("object_id")
    private Integer objectId;


}
