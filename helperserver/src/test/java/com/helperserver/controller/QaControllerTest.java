package com.helperserver.controller;

import com.helperserver.bean.QaKnowledge;
import com.helperserver.service.QaService;
import com.helperserver.utils.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = com.helperserver.HelperServerApplication.class)
public class QaControllerTest {

    @Mock
    private QaService qaService;

    @InjectMocks
    private QaController qaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAskQuestion() {
        // 准备测试数据
        String question = "测试问题";
        String conferenceId = "test-conf-001";
        QaKnowledge expectedResponse = new QaKnowledge();
        expectedResponse.setQaId("1");
        expectedResponse.setQuestion(question);
        expectedResponse.setAnswer("这是测试回答");
        expectedResponse.setScore(0.95f);
        expectedResponse.setConferenceId(conferenceId);

        // 模拟服务层方法
        when(qaService.getAnswer(question, conferenceId)).thenReturn(expectedResponse);

        // 执行测试
        QaKnowledge request = new QaKnowledge();
        request.setQuestion(question);
        request.setConferenceId(conferenceId);
        Object result = qaController.ask(request);

        // 验证结果
        assertNotNull(result);
        if (result instanceof HashMap) {
            HashMap<String, Object> map = (HashMap<String, Object>) result;
            assertEquals(200, map.get("code"));
            assertEquals(true, map.get("success"));
            assertEquals(expectedResponse, map.get("data"));
        } else if (result instanceof Result) {
            Result<?> resultObj = (Result<?>) result;
            assertTrue(resultObj.isSuccess());
            assertEquals(expectedResponse, resultObj.getData());
        } else {
            fail("Unexpected result type: " + result.getClass().getName());
        }
        verify(qaService).getAnswer(question, conferenceId);
    }

    @Test
    void testGetHistory() {
        // 准备测试数据
        String conferenceId = "test-conf-001";
        List<QaKnowledge> expectedHistory = new ArrayList<>();
        QaKnowledge qa1 = new QaKnowledge();
        qa1.setQaId("1");
        qa1.setQuestion("问题1");
        qa1.setAnswer("回答1");
        qa1.setConferenceId(conferenceId);
        expectedHistory.add(qa1);

        // 模拟服务层方法
        when(qaService.getHistory(conferenceId)).thenReturn(expectedHistory);

        // 执行测试
        Result<List<QaKnowledge>> result = qaController.getHistory(conferenceId);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals(expectedHistory, result.getData());
        verify(qaService).getHistory(conferenceId);
    }

    @Test
    void testAskQuestionWithError() {
        // 准备测试数据
        String question = "测试问题";
        String conferenceId = "test-conf-001";
        String errorMessage = "服务暂时不可用";

        // 模拟服务层抛出异常
        when(qaService.getAnswer(question, conferenceId)).thenThrow(new RuntimeException(errorMessage));

        // 执行测试
        QaKnowledge request = new QaKnowledge();
        request.setQuestion(question);
        request.setConferenceId(conferenceId);
        Object result = qaController.ask(request);

        // 验证结果
        assertNotNull(result);
        if (result instanceof HashMap) {
            HashMap<String, Object> map = (HashMap<String, Object>) result;
            assertNotEquals(200, map.get("code"));
        } else if (result instanceof Result) {
            Result<?> resultObj = (Result<?>) result;
            assertFalse(resultObj.isSuccess());
            assertEquals(errorMessage, resultObj.getMessage());
        } else {
            fail("Unexpected result type: " + result.getClass().getName());
        }
        verify(qaService).getAnswer(question, conferenceId);
    }
}