package com.fzcoder.opensource.blog.framework.service;

import com.fzcoder.opensource.blog.framework.params.IParams;
import com.fzcoder.opensource.blog.framework.plugins.IPage;

import java.io.Serializable;
import java.util.List;

public interface IService<E> {
    boolean save(E entity);
    E get(IParams<E> params);
    E getById(Serializable id);
    List<E> getList();
    List<E> getList(IParams<E> params);
    boolean update(E entity);
    boolean remove(IParams<E> params);
    boolean removeById(Serializable id);
    int count();
    int count(IParams<E> params);
    IPage<E> getPage(IParams<E> params);
}
