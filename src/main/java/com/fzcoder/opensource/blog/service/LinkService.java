package com.fzcoder.opensource.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fzcoder.opensource.blog.entity.Link;

import java.util.List;
import java.util.Map;

/**
 * 链接服务的接口类
 * @author User
 *
 */
public interface LinkService extends IService<Link> {

    List<Map<String, Object>> getCategoryGroup();
}
