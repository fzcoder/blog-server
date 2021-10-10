package com.fzcoder.opensource.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fzcoder.opensource.blog.entity.Link;

import java.util.List;
import java.util.Map;

/**
 * 数据库操作的接口类
 * @author Frank Fang
 *
 */
public interface ILinkMapper extends BaseMapper<Link> {

    List<Map<String, Object>> selectCategoryGroupMap();
}
