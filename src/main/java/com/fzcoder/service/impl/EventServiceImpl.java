package com.fzcoder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzcoder.entity.Article;
import com.fzcoder.entity.Event;
import com.fzcoder.mapper.ArticleMapper;
import com.fzcoder.mapper.EventMapper;
import com.fzcoder.service.EventService;
import com.fzcoder.utils.ConstUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Service
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements EventService {

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private ArticleMapper articleMapper;

    private String formatDateToString(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return sdf.format(date);
    }

    @Override
    public boolean handleInsertArticleEvent(Date date, Article article, boolean status) {
        // 添加事件
        Event event = new Event();
        event.setId(date.getTime());
        event.setUid(article.getAuthorId());
        event.setCreateTime(formatDateToString(date, "HH:mm:ss"));
        event.setCreateDate(formatDateToString(date, "yyyy-MM-dd"));
        event.setEventType(ConstUtils.EVENT_TYPE_SUCCESS);
        event.setEventPosition(ConstUtils.EVENT_POSITION_CENTER);
        if (article.getStatus() == ConstUtils.ARTICLE_STATUS_PUBLISHED) {
            event.setEventContent("发布");
            event.setPrefixContent("目录");
            event.setPrefixLink("/article?category_id=" + article.getCategoryId());
            event.setSuffixContent(article.getTitle());
            event.setContribution(ConstUtils.EVENT_CONTRIBUTION_PUBLISHED);
        } else {
            event.setPrefixContent("草稿箱");
            event.setPrefixLink("/article/draft");
            event.setEventContent("添加");
            event.setSuffixContent(article.getTitle());
            event.setContribution(ConstUtils.EVENT_CONTRIBUTION_DRAFT);
        }
        if (status) {
            event.setStatus(true);
            if (article.getStatus() == ConstUtils.ARTICLE_STATUS_DRAFT) {
                event.setSuffixLink("/article/update/" + article.getId());
            } else {
                event.setSuffixLink("/article/preview/" + article.getId());
            }
        } else {
            event.setStatus(false);
            event.setSuffixLink(null);
        }
        return eventMapper.insert(event) > 0;
    }

    @Override
    public boolean handleUpdateArticleEvent(Date date, Article article, boolean status, boolean isDraft) {
        // 添加事件
        Event event = new Event();
        event.setId(date.getTime());
        event.setUid(article.getAuthorId());
        event.setCreateTime(formatDateToString(date, "HH:mm:ss"));
        event.setCreateDate(formatDateToString(date, "yyyy-MM-dd"));

        if (isDraft && article.getStatus() == ConstUtils.ARTICLE_STATUS_PUBLISHED) {
            event.setEventContent("发布");
            event.setEventType(ConstUtils.EVENT_TYPE_SUCCESS);
            event.setEventPosition(ConstUtils.EVENT_POSITION_CENTER);
            event.setPrefixContent("目录");
            event.setPrefixLink("/article?category_id=" + article.getCategoryId());
            event.setContribution(ConstUtils.EVENT_CONTRIBUTION_PUBLISHED);
        } else {
            event.setEventContent("修改");
            event.setEventType(ConstUtils.EVENT_TYPE_PRIMARY);
            event.setContribution(ConstUtils.EVENT_CONTRIBUTION_NONE);
            if (article.getStatus() == ConstUtils.ARTICLE_STATUS_PUBLISHED) {
                event.setEventPosition(ConstUtils.EVENT_POSITION_LEFT);
                event.setPrefixContent(article.getTitle());
                event.setSuffixContent(null);
                event.setSuffixLink(null);
            } else {
                event.setEventPosition(ConstUtils.EVENT_POSITION_CENTER);
                event.setPrefixContent("草稿箱");
                event.setPrefixLink("/article/draft");
                event.setSuffixContent(article.getTitle());
            }
        }
        // 判断操作是否成功
        if (status) {
            event.setStatus(true);
            if (article.getStatus() == ConstUtils.ARTICLE_STATUS_DRAFT) {
                event.setSuffixLink("/article/update/" + article.getId());
            } else {
                if (isDraft) {
                    event.setSuffixLink("/article/preview/" + article.getId());
                } else {
                    event.setPrefixLink("/article/preview/" + article.getId());
                }
            }
        } else {
            event.setStatus(false);
            event.setSuffixLink("/article/update/" + article.getId());
        }
        return eventMapper.insert(event) > 0;
    }

    @Override
    public boolean handleDeleteArticleEvent(Date date, Long articleId, boolean status) {
        Article article = articleMapper.selectById(articleId);
        // 添加事件
        Event event = new Event();
        event.setId(date.getTime());
        event.setUid(article.getAuthorId());
        event.setCreateTime(formatDateToString(date, "HH:mm:ss"));
        event.setCreateDate(formatDateToString(date, "yyyy-MM-dd"));
        event.setEventPosition(ConstUtils.EVENT_POSITION_LEFT);
        event.setEventType(ConstUtils.EVENT_TYPE_DANGER);
        event.setEventContent("删除");
        event.setPrefixContent(article.getTitle());
        event.setPrefixLink(null);
        event.setSuffixContent(null);
        event.setSuffixLink(null);
        event.setContribution(ConstUtils.EVENT_CONTRIBUTION_NONE);
        event.setStatus(status);

        return eventMapper.insert(event) > 0;
    }
}
