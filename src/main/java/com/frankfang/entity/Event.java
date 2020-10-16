package com.frankfang.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * @author Frank Fang
 * 用户事件的实体类
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_event")
@ApiModel(value="Event对象", description="")
public class Event implements Serializable {

    @ApiModelProperty(value = "事件id")
    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "用户id")
    @TableField("uid")
    private Integer uid;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private String createTime;

    @ApiModelProperty(value = "创建日期")
    @TableField("create_date")
    private String createDate;

    @ApiModelProperty(value = "事件类型")
    @TableField("event_type")
    private String eventType;

    @ApiModelProperty(value = "事件内容")
    @TableField("event_content")
    private String eventContent;

    @ApiModelProperty(value = "事件显示位置")
    @TableField("event_position")
    private String eventPosition;

    @ApiModelProperty(value = "前缀内容")
    @TableField("prefix_content")
    private String prefixContent;

    @ApiModelProperty(value = "前缀链接")
    @TableField("prefix_link")
    private String prefixLink;

    @ApiModelProperty(value = "后缀内容")
    @TableField("suffix_content")
    private String suffixContent;

    @ApiModelProperty(value = "后缀链接")
    @TableField("suffix_link")
    private String suffixLink;

    @ApiModelProperty(value = "事件状态")
    @TableField("status")
    private boolean status;

    @ApiModelProperty(value = "事件贡献度")
    @TableField("contribution")
    private int contribution;
}
