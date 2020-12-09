package com.frankfang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.frankfang.entity.Article;
import com.frankfang.view.ArticleView;

import java.util.Map;

/**
 * 文章服务的接口类
 * @author Frank Fang
 * 
 */
public interface ArticleService extends IService<Article> {

    IPage<ArticleView> getPages(Integer uid, String keyword,
                                long pageNum, long pageSize, int status, Map<String, Object> params);
}
