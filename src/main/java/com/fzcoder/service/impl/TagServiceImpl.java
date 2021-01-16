package com.fzcoder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fzcoder.dto.TagForm;
import com.fzcoder.entity.Tag;
import com.fzcoder.mapper.TagMapper;
import com.fzcoder.service.TagService;
import com.fzcoder.utils.IdGenerator;
import com.fzcoder.vo.TagView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public synchronized boolean save(TagForm form) {
        if (form.getTagId() == null || "".equals(form.getTagId())) {
            // 1.生成id
            String tagId = IdGenerator.createIdBy62BaseRandom(6);
            // 2.构建实体对象
            Tag tag = form.build(tagId);
            // 3.检测id是否重复，理论上tag的数量不可能超过62^6
            while (tagMapper.countById(tagId) > 0) {
                log.info("try to insert a new tag in table 'tb_tag'...");
                // 重新生成id
                tagId = IdGenerator.createIdBy62BaseRandom(6);
                tag.setTagId(tagId);
            }
            log.info("insert a new tag in table 'tb_tag' successfully");
            // 4.存入数据库并返回结果
            return tagMapper.insert(tag) > 0;
        } else {
            // 当id存在时直接生成实体对象并插入到数据库中
            return tagMapper.insert(form.build()) > 0;
        }
    }

    @Override
    public synchronized boolean save(TagForm form, Long articleId) {
        // 1.生成id
        String tagId = IdGenerator.createIdBy62BaseRandom(6);
        // 2.检测id是否重复，理论上tag的数量不可能超过62^6
        while (tagMapper.countById(tagId) > 0) {
            log.info("try to insert a new tag in table 'tb_tag'...");
            // 重新生成id
            tagId = IdGenerator.createIdBy62BaseRandom(6);
            form.setTagId(tagId);
        }
        form.setTagId(tagId);
        log.info("insert a new tag in table 'tb_tag' successfully");
        if (this.save(form)) {
            return this.saveRelation(tagId, articleId);
        } else {
            return false;
        }
    }

    @Override
    public synchronized boolean saveRelation(String tagId, Long articleId) {
        return tagMapper.insertRelation(System.currentTimeMillis(), tagId, articleId) > 0;
    }

    @Override
    public void saveRelation(List<String> tagNames, Long articleId) {
        // 遍历标签数组
        for (String tagName : tagNames) {
            // 判断标签是否存在
            if (this.countByName(tagName) == 0) {
                log.info("try to add tags...");
                // 若不存在，添加新的标签并添加关联
                this.save(new TagForm(tagName, ""), articleId);
            } else {
                // 若存在，直接添加关联
                this.saveRelation(this.tagMapper.selectByName(tagName).getTagId(), articleId);
            }
        }
    }

    @Override
    public int count() {
        return tagMapper.selectCount(new QueryWrapper<>());
    }

    @Override
    public int countById(String id) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tag_id", id);
        return tagMapper.selectCount(queryWrapper);
    }

    @Override
    public int countByName(String name) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tag_name", name);
        return tagMapper.selectCount(queryWrapper);
    }

    @Override
    public List<TagView> list(Map<String, Object> params, boolean isRelative) {
        if (isRelative) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if ("article_id".equals(entry.getKey())) {
                    return tagMapper.selectRelationList(entry.getValue());
                }
            }
            return null;
        } else {
            QueryWrapper<TagView> queryWrapper = new QueryWrapper<>();
            queryWrapper.allEq(params);
            return tagMapper.selectViewList(queryWrapper);
        }
    }

    @Override
    public List<TagView> listRelation(Long articleId) {
        Map<String, Object> params = new HashMap<>();
        params.put("article_id", articleId);
        return this.list(params, true);
    }

    @Override
    public void saveOrUpdateRelation(List<String> tagNames, Long articleId) {
        // 1.删除所有tag-article关联项
        removeRelationByAid(articleId);
        // 2.重新添加关联(包括创建新标签)
        this.saveRelation(tagNames, articleId);
    }

    @Override
    public boolean update(Tag tag) {
        // 由于已在数据库中设置外键，当标签id更新时关联会自动更新
        return tagMapper.updateById(tag) > 0;
    }

    @Override
    public boolean removeById(String id) {
        // 由于已在数据库中设置外键，当标签删除时关联自动删除
        return tagMapper.deleteById(id) > 0;
    }

    @Override
    public boolean removeRelationByAid(Long articleId) {
        return tagMapper.deleteRelationByAid(articleId) > 0;
    }
}
