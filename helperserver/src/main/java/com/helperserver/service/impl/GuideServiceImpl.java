package com.helperserver.service.impl;

import com.helperserver.bean.ConferenceGuide;
import com.helperserver.service.GuideService;
import org.springframework.stereotype.Service;

@Service
public class GuideServiceImpl implements GuideService {
    @Override
    public ConferenceGuide getGuide(String conferenceId) {
        // TODO: 实现从数据库获取会议指南信息的逻辑
        ConferenceGuide guide = new ConferenceGuide();
        guide.setConferenceId(conferenceId);
        guide.setGuideId("GUIDE_" + conferenceId);
        
        // 设置会议日程
        guide.setSchedule("Day 1 - 5月17日：\n" +
                "09:00-10:00 开幕式 - 主会场\n" +
                "10:00-12:00 主题演讲：数字安全与AI技术创新\n" +
                "14:00-17:00 平行论坛：AI安全、数据安全、云安全\n\n" +
                "Day 2 - 5月18日：\n" +
                "09:00-12:00 技术研讨会\n" +
                "14:00-17:00 创新成果展示");
        
        // 设置会场规则
        guide.setRules("1. 请佩戴会议证件入场\n" +
                "2. 会议期间请将手机调至静音模式\n" +
                "3. 请遵守会议时间安排，准时参加各项活动\n" +
                "4. 保持会场整洁，爱护会场设施\n" +
                "5. 如需提前离场，请轻声离开");
        
        // 设置餐饮服务信息
        guide.setDiningServices("早餐：07:30-09:00 酒店餐厅\n" +
                "午餐：12:00-13:30 国际会议中心一楼自助餐厅\n" +
                "下午茶：15:30-16:00 各分会场门口\n" +
                "晚餐：18:00-19:30 酒店餐厅");
        
        // 设置其他服务信息
        guide.setOtherServices("WiFi信息：\n" +
                "名称：XihuSecurity2024\n" +
                "密码：XHS@2024\n\n" +
                "会议资料下载：\n" +
                "请扫描会议手册背面二维码\n\n" +
                "技术支持：\n" +
                "会议现场服务台");
        
        // 设置会场地图
        guide.setVenueMap("https://conference.example.com/venue/map");
        
        // 设置交通指南
        guide.setTransportationGuide("会议地点：杭州国际会议中心\n\n" +
                "地铁路线：\n" +
                "地铁2号线国际会议中心站A出口，步行5分钟\n\n" +
                "公交路线：\n" +
                "1. 机场大巴：机场直达线路\n" +
                "2. 公交车：85路、87路到国际会议中心站");
        
        // 设置联系方式
        guide.setContactInfo("会务组：0571-88888888\n" +
                "紧急联系人：王经理 13900000000\n" +
                "邮箱：conference@example.com");
        
        return guide;
    }

    @Override
    public ConferenceGuide updateGuide(ConferenceGuide guide) {
        // TODO: 实现更新会议指南信息的逻辑
        return guide;
    }

    @Override
    public ConferenceGuide createGuide(ConferenceGuide guide) {
        // TODO: 实现创建会议指南信息的逻辑
        return guide;
    }
}