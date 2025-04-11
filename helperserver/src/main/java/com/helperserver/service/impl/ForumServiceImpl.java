package com.helperserver.service.impl;

import com.helperserver.bean.ForumTopic;
import com.helperserver.service.ForumService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.helperserver.mapper.ForumTopicMapper;

import java.util.Calendar;
import org.apache.commons.lang3.time.DateUtils;

@Service
public class ForumServiceImpl implements ForumService {
    

    
    @Autowired
    private ForumTopicMapper forumMapper;

    @Override
    public List<ForumTopic> getHotTopics(String timeRange, int limit) {
        Date startTime = null;
        Date endTime = new Date();
        
        // 根据时间范围设置查询起始时间
        switch (timeRange) {
            case "today":
                startTime = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
                break;
            case "week":
                startTime = DateUtils.addDays(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH), -7);
                break;
            default:
                // 全部时间不设置起始时间限制
                break;
        }
        
        return forumMapper.selectHotTopics(startTime, endTime, limit);
    }
    
    @Override
    public void updateHotScore(String topicId) {
        // 热度分数在ForumTopicMapper的selectHotTopics中已经实现
        // 这里只需要更新时间即可
        Date now = new Date();
        forumMapper.incrementViewCount(topicId, now);
    }
    
    
    @Override
    @Transactional
    public void incrementViewCount(String topicId) {
        Date now = new Date();
        forumMapper.incrementViewCount(topicId, now);
    }
    
    
    @Override
    @Transactional
    public void incrementFavoriteCount(String topicId) {
        Date now = new Date();
        forumMapper.incrementFavoriteCount(topicId, now);
    }
}