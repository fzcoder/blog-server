package com.frankfang.service.impl;

import com.frankfang.bean.Dynamic;
import com.frankfang.mapper.CountMapper;
import com.frankfang.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

@Service
public class CountServiceImpl implements CountService {

    @Autowired
    private CountMapper countMapper;

    @Override
    public List<Dynamic> getDynamicList(Integer uid, String startDate, String endDate) {

        return countMapper.selectDynamicList(uid, startDate, endDate);
    }

    @Override
    public List<Map<String, Object>> getContributionList(Integer uid, String startDate, String endDate) {
        return countMapper.selectContributionList(uid, startDate, endDate);
    }
}
