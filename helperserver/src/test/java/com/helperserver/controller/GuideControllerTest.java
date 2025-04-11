package com.helperserver.controller;

import com.helperserver.bean.ConferenceGuide;
import com.helperserver.service.GuideService;
import com.helperserver.utils.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = com.helperserver.HelperServerApplication.class)
public class GuideControllerTest {

    @Mock
    private GuideService guideService;

    @InjectMocks
    private GuideController guideController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetGuide() {
        // 准备测试数据
        String conferenceId = "test-conf-001";
        ConferenceGuide expectedGuide = new ConferenceGuide();
        expectedGuide.setGuideId("1");
        expectedGuide.setConferenceId(conferenceId);
        expectedGuide.setSchedule("9:00-10:00 开幕式");
        expectedGuide.setRules("请保持安静");

        // 模拟服务层方法
        when(guideService.getGuide(conferenceId)).thenReturn(expectedGuide);

        // 执行测试
        Result<ConferenceGuide> result = guideController.getGuide(conferenceId);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals(expectedGuide, result.getData());
        verify(guideService).getGuide(conferenceId);
    }

    @Test
    void testCreateGuide() {
        // 准备测试数据
        ConferenceGuide guide = new ConferenceGuide();
        guide.setConferenceId("test-conf-001");
        guide.setSchedule("9:00-10:00 开幕式");

        // 模拟服务层方法
        when(guideService.createGuide(guide)).thenReturn(guide);

        // 执行测试
        Result<ConferenceGuide> result = guideController.createGuide(guide);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals(guide, result.getData());
        verify(guideService).createGuide(guide);
    }

    @Test
    void testUpdateGuide() {
        // 准备测试数据
        ConferenceGuide guide = new ConferenceGuide();
        guide.setGuideId("1");
        guide.setConferenceId("test-conf-001");
        guide.setSchedule("10:00-11:00 主题演讲");

        // 模拟服务层方法
        when(guideService.updateGuide(guide)).thenReturn(guide);

        // 执行测试
        Result<ConferenceGuide> result = guideController.updateGuide(guide);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals(guide, result.getData());
        verify(guideService).updateGuide(guide);
    }

    @Test
    void testGetGuideWithError() {
        // 准备测试数据
        String conferenceId = "test-conf-001";
        String errorMessage = "指南信息不存在";

        // 模拟服务层抛出异常
        when(guideService.getGuide(conferenceId)).thenThrow(new RuntimeException(errorMessage));

        // 执行测试
        Result<ConferenceGuide> result = guideController.getGuide(conferenceId);

        // 验证结果
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertEquals(errorMessage, result.getMessage());
        verify(guideService).getGuide(conferenceId);
    }
}