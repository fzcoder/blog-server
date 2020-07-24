package com.frankfang.controller;

import com.frankfang.bean.JsonResponse;
import com.frankfang.entity.Website;
import com.frankfang.service.WebsiteService;
import com.frankfang.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class WebsiteController {

    @Autowired
    private WebsiteService websiteService;

    @GetMapping("/setting")
    public Object getInfo() {
        return new JsonResponse(websiteService.getById(1));
    }

    @PutMapping("/admin/setting")
    public Object setInfo(@RequestBody Website website) {
        website.setId(1);
        if (websiteService.updateById(website)) {
            return new JsonResponse(HttpUtils.Status_OK, "修改成功！");
        } else {
            return new JsonResponse(HttpUtils.Status_InternalServerError, "修改失败！");
        }
    }
}
