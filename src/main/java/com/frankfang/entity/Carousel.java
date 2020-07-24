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
 * @since 2020-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_carousel")
@ApiModel(value="Carousel对象", description="")
public class Carousel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id，作为唯一标识")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "封面")
    @TableField("cover")
    private String cover;

    @ApiModelProperty(value = "链接")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "链接")
    @TableField("title")
    private String title;

}
