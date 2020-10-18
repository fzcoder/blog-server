package com.frankfang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.frankfang.entity.Article;
import com.frankfang.bean.JsonResponse;
import com.frankfang.service.ArticleService;
import com.frankfang.utils.ConstUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class RecommendController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/recommend/article")
    public Object getRecommendArticle() {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        String[] sqlSelect = { "id", "title", "date", "introduction" };
        queryWrapper.select(sqlSelect);
        queryWrapper.orderByDesc("date");
        queryWrapper.eq("status", ConstUtils.ARTICLE_STATUS_PUBLISHED);
        queryWrapper.last("limit 6");
        return new JsonResponse(articleService.listMaps(queryWrapper));
    }

}
