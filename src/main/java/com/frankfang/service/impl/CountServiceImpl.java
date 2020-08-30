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
    public List<Dynamic> getDynamicList(Serializable userId, String type, long partNum) {

        List<Dynamic> result = new LinkedList<>();

        // 查询文章记录
        List<Map<String, Object>> articleList = countMapper.selectArticleRecords(userId);
        HashSet<Dynamic> resultSet = new HashSet<>();
        // 这里利用 Set 的特性对元素进行去重
        for(Map<String, Object> elem : articleList) {
            Dynamic e = new Dynamic();
            e.setTimeStamp(elem.get("create_date").toString());
            resultSet.add(e);
        }

        for (Dynamic elem : resultSet) {
            List<Map<String, Object>> contentList = new LinkedList<>();
            for (Map<String, Object> articleRecord : articleList) {
                if (articleRecord.get("create_date").toString().equals(elem.getTimeStamp())) {
                    contentList.add(articleRecord);
                }
            }
            elem.setType("article");
            // Collections.reverse(contentList);
            elem.setContent(contentList);
        }

        result.addAll(resultSet);
        // 对列表进行倒序排序
        Collections.reverse(result);

        return result;
    }
}
