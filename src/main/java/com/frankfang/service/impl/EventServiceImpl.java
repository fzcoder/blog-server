package com.frankfang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frankfang.entity.Event;
import com.frankfang.mapper.EventMapper;
import com.frankfang.service.EventService;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements EventService {
}
