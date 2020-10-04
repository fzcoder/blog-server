package com.frankfang.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.frankfang.entity.Article;
import com.frankfang.view.ArticleView;
import org.apache.ibatis.annotations.Param;

public interface ArticleMapper extends BaseMapper<Article> {

    /**
     *
     * @author Frank Fang
     * @date 2020-10-03
     * @param page 页面信息
     * @param queryWrapper 查询构造器
     * @return
     */
    IPage<ArticleView> selectPages(IPage<ArticleView> page, @Param(Constants.WRAPPER) Wrapper<ArticleView> queryWrapper);
} 
