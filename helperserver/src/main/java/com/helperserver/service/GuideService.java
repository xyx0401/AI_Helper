package com.helperserver.service;

import com.helperserver.bean.ConferenceGuide;

public interface GuideService {
    /**
     * 获取会议指南信息
     * @param conferenceId 会议ID
     * @return 会议指南信息
     */
    ConferenceGuide getGuide(String conferenceId);
    
    /**
     * 更新会议指南信息
     * @param guide 会议指南信息
     * @return 更新后的会议指南信息
     */
    ConferenceGuide updateGuide(ConferenceGuide guide);
    
    /**
     * 创建会议指南信息
     * @param guide 会议指南信息
     * @return 创建的会议指南信息
     */
    ConferenceGuide createGuide(ConferenceGuide guide);
}