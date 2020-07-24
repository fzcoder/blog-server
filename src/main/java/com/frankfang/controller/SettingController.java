package com.frankfang.controller;

import com.frankfang.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frankfang.entity.Setting;
import com.frankfang.bean.JsonResponse;
import com.frankfang.service.SettingService;

@RestController
@RequestMapping("/api")
public class SettingController {
}
