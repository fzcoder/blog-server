package com.fzcoder.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class Event implements Serializable {

    @TableId("id")
    private Long id;

    @TableField("uid")
    private Integer uid;

    @TableField("create_time")
    private String createTime;

    @TableField("create_date")
    private String createDate;

    @TableField("event_type")
    private String eventType;

    @TableField("event_content")
    private String eventContent;

    @TableField("event_position")
    private String eventPosition;

    @TableField("prefix_content")
    private String prefixContent;

    @TableField("prefix_link")
    private String prefixLink;

    @TableField("suffix_content")
    private String suffixContent;

    @TableField("suffix_link")
    private String suffixLink;

    @TableField("status")
    private boolean status;

    @TableField("contribution")
    private int contribution;
}
