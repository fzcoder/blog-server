package com.fzcoder.utils;

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
     * 事件状态
     */
    String EVENT_TYPE_INFO = "info";

    /**
     *
     */
    String EVENT_TYPE_SUCCESS = "success";

    /**
     *
     */
    String EVENT_TYPE_PRIMARY = "primary";

    /**
     *
     */
    String EVENT_TYPE_WARNING = "warning";

    /**
     *
     */
    String EVENT_TYPE_DANGER = "danger";

    String EVENT_POSITION_LEFT = "left";

    String EVENT_POSITION_CENTER = "center";

    String EVENT_POSITION_RIGHT = "right";

    /**
     *
     */
    int EVENT_CONTRIBUTION_PUBLISHED = 2;

    /**
     *
     */
    int EVENT_CONTRIBUTION_DRAFT = 1;

    /**
     *
     */
    int EVENT_CONTRIBUTION_NONE = 0;
}
