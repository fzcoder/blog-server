package com.fzcoder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fzcoder.entity.Event;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EventMapper extends BaseMapper<Event> {

    List<Event> selectListWithCreateDate(@Param("create_date") String createDate, @Param("uid") Integer uid);
}
