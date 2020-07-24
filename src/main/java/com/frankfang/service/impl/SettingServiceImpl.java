package com.frankfang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import com.frankfang.entity.Setting;
import com.frankfang.mapper.SettingMapper;
import com.frankfang.service.SettingService;

@Service
public class SettingServiceImpl extends ServiceImpl<SettingMapper, Setting> implements SettingService {
}
