package com.fzcoder.opensource.blog.framework.params;

import java.util.Map;

public interface IParams<E> {
    String getSqlSegment();
    String getExtSegment();
    IParams<E> eq(String column, Object value);
    IParams<E> eqAll(Map<String, Object> conditions);
}
