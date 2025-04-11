package com.helperserver.service;

import com.helperserver.bean.Conference;
import com.helperserver.bean.UserPreference;
import java.util.List;

public interface RecommendationService {
    /**
     * 根据用户ID获取用户偏好
     * @param userId 用户ID
     * @return 用户偏好对象
     */
    UserPreference getUserPreference(String userId);

    /**
     * 更新用户偏好
     * @param preference 用户偏好对象
     */
    void updateUserPreference(UserPreference preference);

    /**
     * 根据用户兴趣标签推荐会议内容
     * @param userId 用户ID
     * @return 推荐的会议列表
     */
    List<Conference> getRecommendedConferences(String userId);

    /**
     * 根据用户历史行为更新兴趣标签
     * @param userId 用户ID
     * @param conferenceId 会议ID
     * @param actionType 行为类型（如：浏览、收藏、点赞等）
     */
    void updateUserInterests(String userId, String conferenceId, String actionType);

    /**
     * 获取相似兴趣的用户列表
     * @param userId 用户ID
     * @return 相似兴趣的用户列表
     */
    List<UserPreference> getSimilarUsers(String userId);
}