package com.frankfang.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frankfang.entity.Article;
import com.frankfang.mapper.ArticleMapper;
import com.frankfang.service.ArticleService;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
}
