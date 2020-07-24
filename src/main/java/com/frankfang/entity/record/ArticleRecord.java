package com.frankfang.entity.record;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@TableName("tb_article_record")
@ApiModel(value="ArticleRecord对象", description="")
public class ArticleRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id，作为唯一标识")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "访问时间")
    @TableField("date")
    private String date;

    @ApiModelProperty(value = "访问者的ip地址")
    @TableField("visitor_ip")
    private String visitorIp;

    @ApiModelProperty(value = "文章id")
    @TableField("article_id")
    private Integer articleId;


}
