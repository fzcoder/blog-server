package com.fzcoder.opensource.blog.framework.service;

import com.fzcoder.opensource.blog.framework.mapper.IMapper;
import com.fzcoder.opensource.blog.framework.params.IParams;
import com.fzcoder.opensource.blog.framework.plugins.IPage;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

public class ServiceImpl<M extends IMapper<E>, E> implements IService<E> {
    @Autowired
    protected M mapper;

    @Override
    public boolean save(E entity) {
        return mapper.insert(entity) > 0;
    }

    @Override
    public E get(IParams<E> params) {
        return mapper.select(params);
    }

    @Override
    public E getById(Serializable id) {
        return null;
    }

    @Override
    public List<E> getList() {
        return null;
    }

    @Override
    public List<E> getList(IParams<E> params) {
        return mapper.selectList(params);
    }

    @Override
    public boolean update(E entity) {
        return mapper.update(entity) > 0;
    }

    @Override
    public boolean remove(IParams<E> params) {
        return mapper.delete(params) > 0;
    }

    @Override
    public boolean removeById(Serializable id) {
        return false;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public int count(IParams<E> params) {
        return 0;
    }

    @Override
    public IPage<E> getPage(IParams<E> params) {
        return null;
    }
}
