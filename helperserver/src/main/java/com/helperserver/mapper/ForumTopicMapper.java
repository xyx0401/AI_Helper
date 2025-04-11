package com.helperserver.mapper;

import com.helperserver.bean.ForumTopic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ForumTopicMapper {
    /**
     * 根据ID查询论坛话题
     * @param topicId 话题ID
     * @return 论坛话题
     */
    ForumTopic selectById(String topicId);
    
    /**
     * 查询所有论坛话题
     * @return 论坛话题列表
     */
    List<ForumTopic> selectAll();
    
    /**
     * 根据会议ID查询论坛话题
     * @param conferenceId 会议ID
     * @return 论坛话题列表
     */
    List<ForumTopic> selectByConferenceId(String conferenceId);
    
    /**
     * 查询热门话题
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param limit 限制数量
     * @return 热门话题列表
     */
    List<ForumTopic> selectHotTopics(@Param("startTime") Date startTime, 
                                    @Param("endTime") Date endTime, 
                                    @Param("limit") int limit);
    
    /**
     * 插入论坛话题
     * @param forumTopic 论坛话题
     */
    void insert(ForumTopic forumTopic);
    
    /**
     * 更新论坛话题
     * @param forumTopic 论坛话题
     */
    void update(ForumTopic forumTopic);
    
    /**
     * 删除论坛话题
     * @param topicId 话题ID
     */
    void deleteById(String topicId);
    
    /**
     * 增加浏览次数
     * @param topicId 话题ID
     * @param updatedAt 更新时间
     */
    void incrementViewCount(@Param("topicId") String topicId, @Param("updatedAt") Date updatedAt);
    
    /**
     * 增加收藏数
     * @param topicId 话题ID
     * @param updatedAt 更新时间
     */
    void incrementFavoriteCount(@Param("topicId") String topicId, @Param("updatedAt") Date updatedAt);
    
    /**
     * 更新热度分数
     * @param topicId 话题ID
     * @param hotScore 热度分数
     */
    void updateHotScore(@Param("topicId") String topicId, @Param("hotScore") Double hotScore);
}