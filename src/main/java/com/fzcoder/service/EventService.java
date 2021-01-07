package com.fzcoder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzcoder.entity.Article;
import com.fzcoder.entity.Event;

import java.util.Date;

public interface EventService extends IService<Event> {

    /**
     * 处理添加文章事件
     * @param date
     * @param article
     * @param status
     * @return
     */
    boolean handleInsertArticleEvent(Date date, Article article, boolean status);

    /**
     * 处理修改文章事件
     * @param date
     * @param article
     * @param status
     * @return
     */
    boolean handleUpdateArticleEvent(Date date, Article article, boolean status, boolean isDraft);

    /**
     * 处理文章删除事件
     * @param date
     * @param articleId
     * @param status
     * @return
     */
    boolean handleDeleteArticleEvent(Date date, Long articleId, boolean status);
}
