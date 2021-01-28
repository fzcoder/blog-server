package com.fzcoder.controller;

import com.fzcoder.bean.JsonResponse;
import com.fzcoder.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
