package com.fzcoder.service;

import com.fzcoder.bean.Dynamic;

import java.util.List;
import java.util.Map;

public interface CountService {

    List<Dynamic> getDynamicList(Integer uid, String startDate, String endDate);

    List<Map<String, Object>> getContributionList(Integer uid, String startDate, String endDate);
}
