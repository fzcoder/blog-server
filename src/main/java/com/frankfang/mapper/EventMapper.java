package com.frankfang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.frankfang.entity.Event;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EventMapper extends BaseMapper<Event> {

    List<Event> selectListWithCreateDate(@Param("create_date") String createDate, @Param("uid") Integer uid);
}
