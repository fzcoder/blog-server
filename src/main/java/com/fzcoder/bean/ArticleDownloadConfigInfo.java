package com.fzcoder.bean;

import lombok.Data;

import java.util.Map;

/**
 * 文章下载配置信息类
 */
@Data
public class ArticleDownloadConfigInfo {

    // 是否添加文章附件信息
    boolean withArticleInfo;

    // 信息格式，支持YAML(以'---'作为分隔符), TOML格式(以'+++'作为分隔符)
    String infoFormat;

    // 是否包含title
    boolean withTitle;

    // 是否包含创建时间
    boolean withCreateTime;

    // 是否包含最后一次更新时间
    boolean withUpdateTime;

    // 是否包含文章描述
    boolean withDescription;

    // 是否包含文章封面图片URL
    boolean withCoverUrl;

    // 是否包含目录
    boolean withCategory;

    // 是否包含标签
    boolean withTags;

    // 自定义信息，以键值对形式表示，如不需要可不传入该字段
    Map<String, Object> customInfo;
}
