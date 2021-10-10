package com.fzcoder.opensource.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagView implements Serializable {

    private String tagId;

    private String tagName;
}
