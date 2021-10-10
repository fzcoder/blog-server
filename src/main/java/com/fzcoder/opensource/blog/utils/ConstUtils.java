package com.fzcoder.opensource.blog.utils;

/**
 * 常量接口，用来管理项目中所有的常量
 */
public interface ConstUtils {

    /**
     * 文章为草稿状态
     */
    int ARTICLE_STATUS_DRAFT = 0;

    /**
     * 文章为已发布状态
     */
    int ARTICLE_STATUS_PUBLISHED = 1;

    /**
     * 文章为已删除状态
     */
    int ARTICLE_STATUS_REMOVED = 2;

    /**
     * 记录类型：文章
     */
    String RECORD_TYPE_ARTICLE = "ARTICLE";

    /**
     * 记录类型：目录
     */
    String RECORD_TYPE_CATEGORY = "CATEGORY";

    /**
     * 记录类型：标签
     */
    String RECORD_TYPE_TAG = "TAG";

    /**
     * 操作类型：添加草稿
     */
    String RECORD_OP_DRAFT = "DRAFT";

    /**
     * 操作类型：发布文章
     */
    String RECORD_OP_PUBLISH = "PUBLISH";

    /**
     * 操作类型：更新文章
     */
    String RECORD_OP_UPDATE = "UPDATE";

    /**
     * 操作类型：更新草稿
     */
    String RECORD_OP_UPDATE_DRAFT = "UPDATE_DRAFT";

    /**
     * 操作类型：移除文章
     */
    String RECORD_OP_REMOVE = "REMOVE";

    /**
     * 操作类型：移除草稿
     */
    String RECORD_OP_REMOVE_DRAFT = "REMOVE_DRAFT";

    /**
     * 操作类型：删除文章
     */
    String RECORD_OP_DELETE = "DELETE";

    /**
     * 操作类型：删除草稿
     */
    String RECORD_OP_DELETE_DRAFT = "DELETE_DRAFT";

    /**
     * 操作贡献度：1
     */
    int RECORD_CONTRIBUTION_PUBLISHED = 1;

    /**
     * 操作贡献度：0
     */
    int RECORD_CONTRIBUTION_NONE = 0;
}
