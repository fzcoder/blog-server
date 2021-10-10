package com.fzcoder.opensource.blog.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzcoder.opensource.blog.entity.Link;
import com.fzcoder.opensource.blog.mapper.ILinkMapper;
import com.fzcoder.opensource.blog.service.LinkService;

import java.util.List;
import java.util.Map;

@Service
public class LinkServiceImpl extends ServiceImpl<ILinkMapper, Link> implements LinkService {
    @Override
    public List<Map<String, Object>> getCategoryGroup() {
        return getBaseMapper().selectCategoryGroupMap();
    }
}
