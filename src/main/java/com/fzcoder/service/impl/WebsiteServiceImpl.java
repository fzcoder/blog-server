package com.fzcoder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzcoder.entity.Website;
import com.fzcoder.mapper.WebsiteMapper;
import com.fzcoder.service.WebsiteService;
import org.springframework.stereotype.Service;

@Service
public class WebsiteServiceImpl extends ServiceImpl<WebsiteMapper, Website> implements WebsiteService {
}
