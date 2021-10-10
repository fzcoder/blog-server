package com.fzcoder.opensource.blog.framework.mapper;

import com.fzcoder.opensource.blog.framework.params.IParams;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

public interface IMapper<E> {
    int insert(E entity);
    E selectById(Serializable id);
    E select(@Param("custom_segment") IParams<E> params);
    List<E> selectList(@Param("custom_segment") IParams<E> params);
    int update(E entity);
    int deleteById(Serializable id);
    int delete(@Param("custom_segment") IParams<E> params);
    int count();
    int count(@Param("custom_segment") IParams<E> params);
}
