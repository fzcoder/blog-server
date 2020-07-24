package com.frankfang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.frankfang.bean.ArticleRecommendProperties;
import com.frankfang.entity.Article;
import com.frankfang.mapper.RecommendMapper;
import com.frankfang.bean.JsonResponse;
import com.frankfang.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RequestMapping("/api")
@RestController
public class RecommendController {

    @Autowired
    private RecommendMapper recommendMapper;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/recommend/article")
    public Object getRecommendArticle() {
        List<ArticleRecommendProperties> list = recommendMapper.getProperties();
        List<Map<String, Object>> data = new LinkedList<>();
        for (ArticleRecommendProperties item : list) {
            QueryWrapper<Article> wrapper = new QueryWrapper<>();
            wrapper.eq(true, "id", item.getArticleId());
            String[] sqlSelect = {"id", "title", "introduction", "date"};
            wrapper.select(sqlSelect);
            Map<String, Object> elem = articleService.getMap(wrapper);
            data.add(elem);
        }
        return new JsonResponse(data);
    }

}
