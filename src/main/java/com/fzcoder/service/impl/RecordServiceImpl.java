package com.fzcoder.service.impl;

import com.fzcoder.dto.ArticleForm;
import com.fzcoder.entity.Record;
import com.fzcoder.mapper.RecordMapper;
import com.fzcoder.service.RecordService;
import com.fzcoder.utils.ConstUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Service
public class RecordServiceImpl implements RecordService {

    private RecordMapper recordMapper;

    public RecordServiceImpl(RecordMapper recordMapper) {
        this.recordMapper = recordMapper;
    }

    private String formatDateToString(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return sdf.format(date);
    }

    @Override
    public boolean handleInsertArticleEvent(ArticleForm form, Date date) {
        Record record = new Record(date.getTime(),
                form.getAuthorId(), date, ConstUtils.RECORD_TYPE_ARTICLE, form.getId().toString());
        // 判断是否为发布状态
        if (form.getStatus() == ConstUtils.ARTICLE_STATUS_PUBLISHED) {
            record.setOperationType(ConstUtils.RECORD_OP_PUBLISH);
            record.setContribution(ConstUtils.RECORD_CONTRIBUTION_PUBLISHED);
        } else {
            record.setOperationType(ConstUtils.RECORD_OP_DRAFT);
            record.setContribution(ConstUtils.RECORD_CONTRIBUTION_NONE);
        }
        return recordMapper.insert(record) > 0;
    }

    @Override
    public boolean handleUpdateArticleEvent(ArticleForm form, Date date, Integer beforeStatus) {
        Record record = new Record(date.getTime(),
                form.getAuthorId(), date, ConstUtils.RECORD_TYPE_ARTICLE, form.getId().toString());
        // 判断是否为发布操作
        if (beforeStatus == ConstUtils.ARTICLE_STATUS_DRAFT &&
                form.getStatus() == ConstUtils.ARTICLE_STATUS_PUBLISHED) {
            record.setOperationType(ConstUtils.RECORD_OP_PUBLISH);
            record.setContribution(ConstUtils.RECORD_CONTRIBUTION_PUBLISHED);
        } else {
            record.setContribution(ConstUtils.RECORD_CONTRIBUTION_NONE);
            if (form.getStatus() == ConstUtils.ARTICLE_STATUS_PUBLISHED) {
                // 如何是修改已发布文章，则标记为修改文章
                record.setOperationType(ConstUtils.RECORD_OP_UPDATE);
            } else {
                // 如果是继续修改草稿，则不添加记录
                record.setOperationType(ConstUtils.RECORD_OP_UPDATE_DRAFT);
            }
        }
        return recordMapper.insert(record) > 0;
    }

    @Override
    public boolean handleDeleteArticleEvent(ArticleForm form, Date date) {
        Record record = new Record(date.getTime(),
                form.getAuthorId(), date, ConstUtils.RECORD_TYPE_ARTICLE, form.getId().toString());
        record.setContribution(ConstUtils.RECORD_CONTRIBUTION_NONE);
        if (form.getStatus() == ConstUtils.ARTICLE_STATUS_DRAFT) {
            // 删除草稿
            record.setOperationType(ConstUtils.RECORD_OP_DELETE_DRAFT);
        } else {
            // 删除文章
            record.setOperationType(ConstUtils.RECORD_OP_DELETE);
        }
        return recordMapper.insert(record) > 0;
    }
}
