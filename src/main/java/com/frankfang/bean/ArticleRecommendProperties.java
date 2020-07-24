package com.frankfang.bean;

import lombok.Data;

@Data
public class ArticleRecommendProperties {

    private Integer articleId;

    private Long hits;

    private Long like;
}
