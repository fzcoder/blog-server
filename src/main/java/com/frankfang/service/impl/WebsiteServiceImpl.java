package com.frankfang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frankfang.entity.Website;
import com.frankfang.mapper.WebsiteMapper;
import com.frankfang.service.WebsiteService;
import org.springframework.stereotype.Service;

@Service
public class WebsiteServiceImpl extends ServiceImpl<WebsiteMapper, Website> implements WebsiteService {
}
