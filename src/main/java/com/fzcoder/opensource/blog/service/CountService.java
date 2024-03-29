package com.fzcoder.opensource.blog.service;

import com.fzcoder.opensource.blog.bean.Dynamic;

import java.util.List;
import java.util.Map;

public interface CountService {

    List<Dynamic> getDynamicList(Integer uid, String startDate, String endDate);

    List<Map<String, Object>> getContributionList(Integer uid, String startDate, String endDate);
}
