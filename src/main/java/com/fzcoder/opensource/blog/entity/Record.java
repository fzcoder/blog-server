package com.fzcoder.opensource.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Data
@TableName("tb_record")
public class Record {

    @TableId("id")
    private Long id;

    @TableField("uid")
    private Integer uid;

    @TableField("create_time")
    private String createTime;

    @TableField("create_date")
    private String createDate;

    @TableField("record_type")
    private String recordType;

    @TableField("operation_type")
    private String operationType;

    @TableField("object_id")
    private String objectId;

    @TableField("contribution")
    private int contribution;

    private String formatDateToString(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return sdf.format(date);
    }

    public Record() {
    }

    public Record(Long id, Integer uid, Date date, String recordType, String objectId) {
        this.id = id;
        this.uid = uid;
        this.createDate = formatDateToString(date, "yyyy-MM-dd");
        this.createTime = formatDateToString(date, "HH:mm:ss");
        this.recordType = recordType;
        this.objectId = objectId;
    }
}
