package com.fzcoder.opensource.blog.service.impl;

import com.fzcoder.opensource.blog.bean.Dynamic;
import com.fzcoder.opensource.blog.mapper.ICountMapper;
import com.fzcoder.opensource.blog.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CountServiceImpl implements CountService {

    @Autowired
    private ICountMapper countMapper;

    @Override
    public List<Dynamic> getDynamicList(Integer uid, String startDate, String endDate) {

        return countMapper.selectDynamicList(uid, startDate, endDate);
    }

    @Override
    public List<Map<String, Object>> getContributionList(Integer uid, String startDate, String endDate) {
        return countMapper.selectContributionList(uid, startDate, endDate);
    }
}
