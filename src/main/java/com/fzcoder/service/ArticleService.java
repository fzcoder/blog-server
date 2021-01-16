package com.fzcoder.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fzcoder.bean.ArticleDownloadConfigInfo;
import com.fzcoder.dto.ArticleForm;
import com.fzcoder.entity.Article;
import com.fzcoder.vo.ArticleView;
import com.fzcoder.vo.Post;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 文章服务的接口类
 * @author Frank Fang
 * 
 */
public interface ArticleService extends IService<Article> {

    /**
     * 添加文章
     * @param form
     * @return
     */
    boolean save(ArticleForm form);

    /**
     * 根据id获取文章视图
     * @param id
     * @return
     */
    Post getViewById(Long id);

    /**
     * 通过id获取文章表单
     * @param id
     * @return
     */
    ArticleForm getFormById(Long id);

    /**
     * 获取文章列表
     * @param uid
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @param status
     * @param params
     * @return
     */
    IPage<ArticleView> getPages(Integer uid, String keyword,
                                long pageNum, long pageSize, int status, Map<String, Object> params);

    /**
     * 上传文章
     * @param request
     * @param response
     */
    void upload(HttpServletRequest request, HttpServletResponse response);

    /**
     * 下载文章
     * @param articleId
     * @param response
     * @param info
     */
    void download(Long articleId, HttpServletResponse response, ArticleDownloadConfigInfo info);

    /**
     * 更新文章
     * @param form
     * @return
     */
    boolean update(ArticleForm form);

    /**
     * 根据id删除文章
     * @param id
     * @return
     */
    boolean removeById(Long id);
}
