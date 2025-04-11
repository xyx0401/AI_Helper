package com.helperserver.service;

import com.helperserver.bean.ForumTopic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ForumService {
    /**
     * 获取热门论坛议题排行榜
     * @param timeRange 时间范围：today-今日, week-本周, all-全部
     * @param limit 返回数量限制
     * @return 排序后的论坛议题列表
     */
    List<ForumTopic> getHotTopics(String timeRange, int limit);
    
    /**
     * 更新论坛议题热度分数
     * @param topicId 议题ID
     */
    void updateHotScore(String topicId);
    
    /**
     * 增加论坛议题浏览次数
     * @param topicId 议题ID
     */
    void incrementViewCount(String topicId);
    

    
    /**
     * 增加论坛议题收藏数
     * @param topicId 议题ID
     */
    void incrementFavoriteCount(String topicId);
}