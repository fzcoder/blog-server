package com.frankfang.controller;

import com.frankfang.bean.JsonResponse;
import com.frankfang.service.*;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "统计模块接口")
@RequestMapping("/api")
@RestController
public class CountController {

    @Autowired
    private CountService countService;


    @GetMapping("/admin/dynamic")
    public Object getDynamic(@RequestParam("uid") Integer uid, @RequestParam("start_date") String startDate, @RequestParam("end_date") String endDate) {
        return new JsonResponse(countService.getDynamicList(uid, startDate, endDate));
    }

    @GetMapping("/admin/chart/contribution")
    public Object getContribution(@RequestParam("uid") Integer uid, @RequestParam("start_date") String startDate, @RequestParam("end_date") String endDate) {
        return new JsonResponse(countService.getContributionList(uid, startDate, endDate));
    }
}
