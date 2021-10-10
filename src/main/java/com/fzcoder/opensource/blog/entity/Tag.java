package com.fzcoder.opensource.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_tag")
public class Tag implements Serializable {

    // 标签id
    @TableId("tag_id")
    private String tagId;

    // 标签名称
    @TableField("tag_name")
    private String tagName;

    // 标签描述
    @TableField("description")
    private String description;
}
