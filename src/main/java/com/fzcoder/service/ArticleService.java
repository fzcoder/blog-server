package com.fzcoder.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fzcoder.bean.ArticleDownloadConfigInfo;
import com.fzcoder.entity.Article;
import com.fzcoder.view.ArticleView;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 文章服务的接口类
 * @author Frank Fang
 * 
 */
public interface ArticleService extends IService<Article> {

    IPage<ArticleView> getPages(Integer uid, String keyword,
                                long pageNum, long pageSize, int status, Map<String, Object> params);

    void download(Long articleId, HttpServletResponse response, ArticleDownloadConfigInfo info);
}
