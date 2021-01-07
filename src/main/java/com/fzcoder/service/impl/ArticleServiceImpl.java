package com.fzcoder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzcoder.view.ArticleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzcoder.entity.Article;
import com.fzcoder.mapper.ArticleMapper;
import com.fzcoder.service.ArticleService;

import java.util.Map;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public IPage<ArticleView> getPages(Integer uid, String keyword,
                                       long pageNum, long pageSize, int status, Map<String, Object> params) {
        QueryWrapper<ArticleView> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("author_id", uid);
        queryWrapper.eq("status", status);
        queryWrapper.like("title", keyword);
        if (params.containsKey("order_by") && params.containsKey("is_reverse")) {
            queryWrapper.orderBy(true,
                    params.get("is_reverse").toString().equals("false"),
                    params.get("order_by").toString());
        }
        // 当路径参数中存在category_id时
        if (params.containsKey("category_id")) {
            if (params.get("category_id").toString().equals("default")) {
                queryWrapper.isNull("category_id");
            } else {
                queryWrapper.eq("category_id", params.get("category_id"));
            }
        }
        return articleMapper.selectPages(new Page<>(pageNum, pageSize), queryWrapper);
    }
}
