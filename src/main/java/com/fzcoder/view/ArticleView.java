package com.fzcoder.view;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ArticleView implements Serializable {

    /**
     * 实现序列化接口
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id，作为唯一的表示")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty(value = "作者id")
    @TableField("author")
    private UserView author;

    @ApiModelProperty(value = "文章标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "创建日期")
    @TableField("createTime")
    private String createTime;

    @ApiModelProperty(value = "最后更新时间")
    @TableField("updateTime")
    private String updateTime;

    @ApiModelProperty(value = "文章简介")
    @TableField("introduction")
    private String introduction;

    @ApiModelProperty(value = "目录id")
    @TableField("category")
    private CategoryView category;

    @ApiModelProperty(value = "文章标签，以英文逗号分隔")
    @TableField("tags")
    private String tags;

    @ApiModelProperty(value = "封面链接")
    @TableField("cover")
    private String cover;
}
