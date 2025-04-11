package com.helperserver.mapper;

import com.helperserver.bean.Forum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ForumMapper {
    /**
     * 根据ID查询论坛
     * @param forumId 论坛ID
     * @return 论坛
     */
    Forum selectById(String forumId);
    
    /**
     * 查询所有论坛
     * @return 论坛列表
     */
    List<Forum> selectAll();
    
    /**
     * 根据标题查询论坛
     * @param title 标题
     * @return 论坛列表
     */
    List<Forum> selectByTitle(String title);
    
    /**
     * 根据标签查询论坛
     * @param tag 标签
     * @return 论坛列表
     */
    List<Forum> selectByTag(String tag);
    
    /**
     * 插入论坛
     * @param forum 论坛
     */
    void insert(Forum forum);
    
    /**
     * 更新论坛
     * @param forum 论坛
     */
    void update(Forum forum);
    
    /**
     * 删除论坛
     * @param forumId 论坛ID
     */
    void deleteById(String forumId);
    
    /**
     * 增加浏览次数
     * @param forumId 论坛ID
     * @param updatedAt 更新时间
     */
    void incrementViewCount(@Param("forumId") String forumId, @Param("updatedAt") Date updatedAt);
    
    /**
     * 增加收藏数
     * @param forumId 论坛ID
     * @param updatedAt 更新时间
     */
    void incrementFavoriteCount(@Param("forumId") String forumId, @Param("updatedAt") Date updatedAt);
}