package com.fzcoder.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.fzcoder.entity.Tag;
import com.fzcoder.vo.TagView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 在tb_tag_article表中插入一套记录
     * @param timestamp
     * @param tagId
     * @param articleId
     * @return
     */
    int insertRelation(@Param("timestamp") long timestamp,
                     @Param("tagId") String tagId, @Param("articleId") Long articleId);

    /**
     *
     * @param id
     * @return
     */
    int countById(String id);

    /**
     * 根据标签名称查询个数
     * @param name
     * @return
     */
    int countByName(String name);

    /**
     * 通过名称查找tag
     * @param name
     * @return
     */
    Tag selectByName(String name);


    /**
     *
     * @param queryWrapper
     * @return
     */
    List<TagView> selectViewList(@Param(Constants.WRAPPER) Wrapper<TagView> queryWrapper);

    /**
     *
     * @param articleId
     * @return
     */
    List<TagView> selectRelationList(@Param("articleId") Object articleId);

    /**
     * 根据文章id删除关联
     * @param articleId
     * @return
     */
    int deleteRelationByAid(Long articleId);
}
