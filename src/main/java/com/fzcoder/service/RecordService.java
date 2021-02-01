package com.fzcoder.service;

import com.fzcoder.dto.ArticleForm;

import java.util.Date;

public interface RecordService {

    /**
     *
     * @param form
     * @param date
     * @return
     */
    boolean handleInsertArticleEvent(ArticleForm form, Date date);

    /**
     *
     * @param form
     * @param date
     * @param beforeStatus
     * @return
     */
    boolean handleUpdateArticleEvent(ArticleForm form, Date date, Integer beforeStatus);

    /**
     *
     * @param articleId
     * @param date
     * @return
     */
    boolean handleDeleteArticleEvent(ArticleForm form, Date date);
}
