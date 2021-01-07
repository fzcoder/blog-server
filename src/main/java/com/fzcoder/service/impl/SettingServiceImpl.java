package com.fzcoder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import com.fzcoder.entity.Setting;
import com.fzcoder.mapper.SettingMapper;
import com.fzcoder.service.SettingService;

@Service
public class SettingServiceImpl extends ServiceImpl<SettingMapper, Setting> implements SettingService {
}
