package com.fzcoder.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.fzcoder.entity.Article;
import com.fzcoder.vo.ArticleView;
import com.fzcoder.vo.Post;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {

    /**
     *
     * @param id
     * @return
     */
    Post selectPostById(Long id);

    /**
     *
     * @param uid
     * @param date
     * @return
     */
    List<ArticleView> selectListByDate(@Param("uid") Long uid, @Param("create_date") String date);

    /**
     * 获取文章列表
     * @author Frank Fang
     * @date 2020-10-03
     * @param page 页面信息
     * @param queryWrapper 查询构造器
     * @return
     */
    IPage<ArticleView> selectPages(IPage<ArticleView> page, @Param(Constants.WRAPPER) Wrapper<ArticleView> queryWrapper);

    /**
     * 根据id返回文章数量
     * @param id
     * @return
     */
    int countById(Serializable id);
} 
