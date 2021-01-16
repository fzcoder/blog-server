package com.fzcoder.dto;

import com.fzcoder.entity.Article;
import com.fzcoder.entity.Tag;
import com.fzcoder.vo.TagView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleForm implements Serializable {

    private static final long serialVersionUID = 1L;

    // 文章id
    private Long id;

    // 作者id
    private Integer authorId;

    // 文章标题
    private String title;

    // 文章简介
    private String introduction;

    // 目录id
    private String categoryId;

    // 标签列表
    private List<String> tags;

    // 封面URL
    private String cover;

    // HTML格式的内容
    private String contentHtml;

    // Markdown格式的内容
    private String contentMd;

    // 文章状态
    private int status;

    /**
     * 根据实体对象构造表单
     * @param article 实体对象
     */
    public ArticleForm(Article article, List<TagView> tags) {
        this.id = article.getId();
        this.authorId = article.getAuthorId();
        this.title = article.getTitle();
        this.introduction = article.getIntroduction();
        this.categoryId = article.getCategoryId();
        this.cover = article.getCover();
        this.contentHtml = article.getContentHtml();
        this.contentMd = article.getContentMd();
        List<String> tagList = new ArrayList<>();
        for (TagView tag : tags) {
            tagList.add(tag.getTagName());
        }
        this.tags = tagList;
        this.status = article.getStatus();
    }

    // 构建一个Article对象
    public Article build() {
        return this.build(this.id);
    }

    // 构建一个Article对象
    public Article build(Long articleId) {
        // 1.实例化article对象
        Article article = new Article();
        // 2.将表单内容填入article对象中
        article.setId(articleId);
        article.setAuthorId(this.authorId);
        article.setTitle(this.title);
        article.setIntroduction(this.introduction);
        article.setCover(this.cover);
        article.setCategoryId(this.categoryId);
        article.setContentHtml(this.contentHtml);
        article.setContentMd(this.contentMd);
        article.setStatus(this.status);
        // 3.返回article对象
        return article;
    }
}
