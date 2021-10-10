package com.fzcoder.opensource.blog.dto;

import com.fzcoder.opensource.blog.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagForm implements Serializable {
    // 标签id
    private String tagId;
    // 标签名称
    private String tagName;
    // 描述
    private String description;

    /**
     *
     * @param tagName
     * @param description
     */
    public TagForm(String tagName, String description) {
        this.tagId = null;
        this.tagName = tagName;
        this.description = description;
    }

    /**
     * 构造Tag对象
     * @return
     */
    public Tag build() {
        return this.build(this.tagId);
    }

    /**
     * 构造Tag对象
     * @param id
     * @return
     */
    public Tag build(String id) {
        return new Tag(id, this.tagName, this.description);
    }
}
