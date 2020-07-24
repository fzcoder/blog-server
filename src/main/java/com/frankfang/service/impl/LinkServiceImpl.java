package com.frankfang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frankfang.entity.Link;
import com.frankfang.mapper.LinkMapper;
import com.frankfang.service.LinkService;

import java.util.List;
import java.util.Map;

@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Autowired
    private LinkMapper linkMapper;

    @Override
    public List<Map<String, Object>> getCategoryGroup() {
        return linkMapper.selectCategoryGroupMap();
    }
}
