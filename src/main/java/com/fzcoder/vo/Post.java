package com.fzcoder.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 页面展示的文章类
 * @author Frank Fang
 * @since 2.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post extends ArticleView {
    // HTML格式内容
    private String contentHtml;
}
