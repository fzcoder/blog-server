package com.fzcoder.opensource.blog.service;

import com.fzcoder.opensource.blog.dto.TagForm;
import com.fzcoder.opensource.blog.entity.Tag;
import com.fzcoder.opensource.blog.vo.TagView;

import java.util.List;
import java.util.Map;

public interface TagService {

    /**
     * 保存一个标签
     * @param form
     * @return
     */
    boolean save(TagForm form);

    /**
     *
     * @param form
     * @param articleId
     * @return
     */
    boolean save(TagForm form, Long articleId);

    /**
     * 保存关联
     * @param tagId
     * @param articleId
     * @return
     */
    boolean saveRelation(String tagId, Long articleId);

    /**
     * 保存关联
     * @param tagNames
     * @param articleId
     */
    void saveRelation(List<String> tagNames, Long articleId);

    /**
     * 查询所有标签数
     * @return
     */
    int count();

    /**
     * 根据标签id查询数量
     * @param id
     * @return
     */
    int countById(String id);

    /**
     * 根据标签名称查询数量
     * @param name
     * @return
     */
    int countByName(String name);

    /**
     * 根据参数获取标签列表
     * @param params
     * @param isRelative
     * @return
     */
    List<TagView> list(Map<String, Object> params, boolean isRelative);

    /**
     * 根据文章id获取关联的标签列表
     * @param articleId
     * @return
     */
    List<TagView> listRelation(Long articleId);

    /**
     * 添加或保存关联
     * @param tagNames
     * @param articleId
     */
    void saveOrUpdateRelation(List<String> tagNames, Long articleId);

    /**
     * 更新标签
     * @param tag
     * @return
     */
    boolean update(Tag tag);

    /**
     * 根据id删除标签
     * @param id
     * @return
     */
    boolean removeById(String id);

    /**
     * 根据文章id删除所有关联项
     * @param articleId
     * @return
     */
    boolean removeRelationByAid(Long articleId);
}
