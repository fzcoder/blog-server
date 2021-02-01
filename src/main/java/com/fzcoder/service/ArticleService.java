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
import java.util.Date;
import java.util.Map;

/**
 * 文章服务的接口类
 * @author Frank Fang
 * 
 */
public interface ArticleService extends IService<Article> {

    /**
     * 保存文章
     * @param form 表单
     * @param date 日期
     * @return
     */
    boolean save(ArticleForm form, Date date);

    /**
     * 根据id获取文章视图
     * @param id 文章id
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
     * @param uid 作者id
     * @param keyword 关键字
     * @param pageNum 页数
     * @param pageSize 每页的数量
     * @param status 状态
     * @param params 参数
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
     *  修改文章
     * @param form 表单
     * @param date 日期
     * @param beforeStatus 修改前的状态
     * @return
     */
    boolean update(ArticleForm form, Date date, Integer beforeStatus);


    /**
     * 删除文章
     * @param form 表单
     * @param date 日期
     * @return
     */
    boolean removeById(ArticleForm form, Date date);
}
