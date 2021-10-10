package com.fzcoder.opensource.blog.mapper;

import com.fzcoder.opensource.blog.bean.Dynamic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Mapper
public interface ICountMapper {

    List<Map<String, Object>> selectArticleRecords(@Param("uid") Serializable uid);

    List<Dynamic> selectDynamicList(@Param("uid") Integer uid, @Param("start_date") String startDate, @Param("end_date") String endDate);

    List<Map<String, Object>> selectContributionList(@Param("uid") Integer uid, @Param("start_date") String startDate, @Param("end_date") String endDate);
}
