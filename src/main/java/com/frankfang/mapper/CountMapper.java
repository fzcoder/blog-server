package com.frankfang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Mapper
public interface CountMapper {

    List<Map<String, Object>> selectArticleRecords(@Param("uid") Serializable uid);
}
