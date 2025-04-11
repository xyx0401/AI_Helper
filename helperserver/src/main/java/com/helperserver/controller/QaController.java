package com.helperserver.controller;

import com.helperserver.bean.QaKnowledge;
import com.helperserver.service.QaService;
import com.helperserver.utils.Result;
import java.util.List;
import java.util.ArrayList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qa")
@Api(tags = "AI问答接口")
public class QaController {
    private static final Logger logger = LoggerFactory.getLogger(QaController.class);

    @Autowired
    private QaService qaService;

    @PostMapping("/ask")
    @ApiOperation("提交问题")
    public Result<QaKnowledge> ask(@RequestBody QaKnowledge request) {
        logger.info("收到问题: {}", request.getQuestion());
        
        try {
            QaKnowledge response = qaService.getAnswer(request.getQuestion(), request.getConferenceId());
            
            // 获取AI回答内容
            String answer = response.getAnswer();
            logger.info("返回AI回答, 长度: {}", answer.length());
            
            // 检查并移除可能的额外引号
            if (answer.startsWith("\"") && answer.endsWith("\"")) {
                answer = answer.substring(1, answer.length() - 1);
                response.setAnswer(answer);
                logger.info("已移除AI回答的额外引号");
            }
            
            // 回答为空或包含错误关键字，返回友好回复
            if (answer == null || answer.isEmpty() || answer.contains("error") || answer.contains("错误") || answer.contains("失败")) {
                logger.warn("AI回答异常，原始回答: {}", answer);
                answer = "抱歉，我现在无法回答这个问题。";
                response.setAnswer(answer);
            }
            
            // 返回Result对象，确保消息为AI回答
            return new Result<>(200, answer, response);
        } catch (Exception e) {
            // 记录错误日志但发送友好回复
            logger.error("处理问题时发生错误", e);
            
            // 创建友好的回复，不包含技术细节
            QaKnowledge errorResponse = new QaKnowledge();
            errorResponse.setQuestion(request.getQuestion());
            errorResponse.setAnswer("抱歉，我现在无法回答这个问题。");
            errorResponse.setConferenceId(request.getConferenceId());
            errorResponse.setTimestamp(System.currentTimeMillis());
            
            return new Result<>(200, errorResponse.getAnswer(), errorResponse);
        }
    }

    @GetMapping("/history")
    @ApiOperation("获取问答历史")
    public Result<List<QaKnowledge>> getHistory(@RequestParam(required = false) String conferenceId) {
        try {
            List<QaKnowledge> history = qaService.getHistory(conferenceId);
            
            // 对历史记录中的每个回答进行检查和过滤
            history.forEach(qa -> {
                String answer = qa.getAnswer();
                if (answer == null || answer.isEmpty() || answer.contains("error") || answer.contains("错误") || answer.contains("失败")) {
                    qa.setAnswer("抱歉，无法显示该回答。");
                }
            });
            
            return new Result<>(200, "已获取历史记录: " + history.size() + "条", history);
        } catch (Exception e) {
            // 记录错误但返回空列表
            logger.error("获取历史记录时发生错误", e);
            return new Result<>(200, "未找到历史记录", new ArrayList<>());
        }
    }
    
    // 添加测试接口
    @GetMapping("/test")
    @ApiOperation("测试AI连接")
    public Result<String> test() {
        logger.info("测试AI连接");
        try {
            QaKnowledge response = qaService.getAnswer("测试", "test-session");
            String answer = response.getAnswer();
            logger.info("测试AI回答: {}", answer);
            
            // 回答为空或包含错误关键字，返回友好回复
            if (answer == null || answer.isEmpty() || answer.contains("error") || answer.contains("错误") || answer.contains("失败")) {
                logger.warn("测试回答异常，原始回答: {}", answer);
                answer = "抱歉，我现在无法回答这个问题。";
            }
            
            // 直接使用answer作为消息和数据
            return new Result<>(200, answer, answer);
        } catch (Exception e) {
            // 记录错误日志但发送友好回复
            logger.error("测试连接失败", e);
            String friendlyResponse = "抱歉，我现在无法回答这个问题。";
            return new Result<>(200, friendlyResponse, friendlyResponse);
        }
    }
}