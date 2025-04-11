package com.helperserver.service.impl;

import com.helperserver.bean.Conference;
import com.helperserver.bean.UserPreference;
import com.helperserver.mapper.ConferenceMapper;
import com.helperserver.mapper.UserPreferenceMapper;
import com.helperserver.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    private UserPreferenceMapper userPreferenceMapper;

    @Autowired
    private  ConferenceMapper conferenceMapper;

    @Override
    public UserPreference getUserPreference(String userId) {
        return userPreferenceMapper.findByUserId(userId);
    }

    @Override
    public void updateUserPreference(UserPreference preference) {
        UserPreference existingPreference = userPreferenceMapper.findByUserId(preference.getUserId());
        if (existingPreference == null) {
            preference.setPreferenceId(UUID.randomUUID().toString());
            userPreferenceMapper.insert(preference);
        } else {
            userPreferenceMapper.update(preference);
        }
    }

    @Override
    public List<Conference> getRecommendedConferences(String userId) {
        UserPreference userPreference = getUserPreference(userId);
        if (userPreference == null || userPreference.getInterests() == null) {
            return new ArrayList<>();
        }

        // 将用户兴趣标签拆分为列表
        List<String> userInterests = Arrays.asList(userPreference.getInterests().split(","));
        
        // 基于内容的推荐：匹配会议标签和用户兴趣标签
        List<Conference> contentBasedRecommendations = new ArrayList<>();
        for (String interest : userInterests) {
            contentBasedRecommendations.addAll(conferenceMapper.selectByTag(interest.trim()));
        }
        
        // 协同过滤：获取相似用户喜欢的会议
        List<UserPreference> similarUsers = getSimilarUsers(userId);
        List<String> similarUserIds = similarUsers.stream()
                .map(UserPreference::getUserId)
                .collect(Collectors.toList());
        List<Conference> collaborativeRecommendations = new ArrayList<>();
        for (String similarUserId : similarUserIds) {
            collaborativeRecommendations.addAll(conferenceMapper.findRecommendedByUserId(similarUserId));
        }
        
        // 混合推荐：合并两种推荐结果并去重
        Set<Conference> recommendedConferences = new LinkedHashSet<>();
        recommendedConferences.addAll(contentBasedRecommendations);
        recommendedConferences.addAll(collaborativeRecommendations);
        
        // 按相关度排序并限制返回数量
        return recommendedConferences.stream()
                .sorted((c1, c2) -> calculateRelevanceScore(c2, userInterests) - calculateRelevanceScore(c1, userInterests))
                .limit(10)
                .collect(Collectors.toList());
    }
    
    private int calculateRelevanceScore(Conference conference, List<String> userInterests) {
        int score = 0;
        List<String> conferenceTags = Arrays.asList(conference.getTags().split(","));
        for (String tag : conferenceTags) {
            if (userInterests.contains(tag.trim())) {
                score++;
            }
        }
        return score;
    }

    @Override
    public void updateUserInterests(String userId, String conferenceId, String actionType) {
        UserPreference preference = getUserPreference(userId);
        if (preference == null) {
            preference = new UserPreference();
            preference.setUserId(userId);
            preference.setPreferenceId(UUID.randomUUID().toString());
        }

        // 获取会议的标签信息
        Conference conference = conferenceMapper.selectById(conferenceId);
        if (conference == null || conference.getTags() == null) {
            return;
        }
        
        // 获取现有的兴趣标签
        Set<String> currentInterests = new HashSet<>();
        if (preference.getInterests() != null) {
            currentInterests.addAll(Arrays.asList(preference.getInterests().split(",")));
        }
        
        // 根据行为类型更新兴趣标签权重
        List<String> conferenceTags = Arrays.asList(conference.getTags().split(","));
        for (String tag : conferenceTags) {
            String trimmedTag = tag.trim();
            switch (actionType.toLowerCase()) {
                case "view":
                    currentInterests.add(trimmedTag);
                    break;
                case "like":
                case "favorite":
                    currentInterests.add(trimmedTag);
                    // 对于重要行为，可以添加相关标签
                    addRelatedTags(currentInterests, trimmedTag);
                    break;
                case "dislike":
                    currentInterests.remove(trimmedTag);
                    break;
            }
        }
        
        // 更新用户兴趣标签
        preference.setInterests(String.join(",", currentInterests));
        userPreferenceMapper.update(preference);
    }
    
    private void addRelatedTags(Set<String> interests, String tag) {
        // 这里可以添加与当前标签相关的其他标签
        List<Conference> relatedConferences = conferenceMapper.selectByTag(tag);
        for (Conference conference : relatedConferences) {
            if (conference.getTags() != null) {
                interests.addAll(Arrays.asList(conference.getTags().split(",")));
            }
        }
    }

    @Override
    public List<UserPreference> getSimilarUsers(String userId) {
        UserPreference userPreference = getUserPreference(userId);
        if (userPreference == null || userPreference.getInterests() == null) {
            return new ArrayList<>();
        }

        // 获取用户的兴趣标签
        String[] interests = userPreference.getInterests().split(",");

        // 查找具有相似兴趣的用户
        Set<UserPreference> similarUsers = new HashSet<>();
        for (String interest : interests) {
            List<UserPreference> users = userPreferenceMapper.findByInterest(interest.trim());
            similarUsers.addAll(users);
        }

        // 移除当前用户
        similarUsers.removeIf(user -> user.getUserId().equals(userId));

        // 按兴趣相似度排序（可以进一步优化相似度计算算法）
        return similarUsers.stream()
                .sorted((u1, u2) -> {
                    int similarity1 = calculateSimilarity(userPreference, u1);
                    int similarity2 = calculateSimilarity(userPreference, u2);
                    return Integer.compare(similarity2, similarity1);
                })
                .collect(Collectors.toList());
    }

    private int calculateSimilarity(UserPreference user1, UserPreference user2) {
        if (user1.getInterests() == null || user2.getInterests() == null) {
            return 0;
        }

        Set<String> interests1 = new HashSet<>(Arrays.asList(user1.getInterests().split(",")));
        Set<String> interests2 = new HashSet<>(Arrays.asList(user2.getInterests().split(",")));

        // 计算交集大小作为相似度指标
        Set<String> intersection = new HashSet<>(interests1);
        intersection.retainAll(interests2);
        return intersection.size();
    }
}