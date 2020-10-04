package com.frankfang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.frankfang.view.ArticleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frankfang.entity.Article;
import com.frankfang.mapper.ArticleMapper;
import com.frankfang.service.ArticleService;

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
        return articleMapper.selectPages(new Page<>(pageNum, pageSize), queryWrapper);
    }
}
