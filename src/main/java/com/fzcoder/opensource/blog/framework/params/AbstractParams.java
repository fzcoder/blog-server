package com.fzcoder.opensource.blog.framework.params;

import java.util.Map;

public abstract class AbstractParams<E> implements IParams<E> {
    private String sqlSegment;
    private String extSegment;

    public AbstractParams() {
        sqlSegment = "";
        extSegment = "";
    }

    @Override
    public String getSqlSegment() {
        return sqlSegment;
    }

    @Override
    public String getExtSegment() {
        return extSegment;
    }

    @Override
    public IParams<E> eq(String column, Object value) {
        return this;
    }

    @Override
    public IParams<E> eqAll(Map<String, Object> conditions) {
        return this;
    }
}
