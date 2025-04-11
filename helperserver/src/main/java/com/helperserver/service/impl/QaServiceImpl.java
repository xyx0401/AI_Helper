package com.helperserver.service.impl;

import com.helperserver.bean.QaKnowledge;
import com.helperserver.service.QaService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.Base64;
import java.net.HttpURLConnection;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@Service
public class QaServiceImpl implements QaService {
    private static final Logger logger = LoggerFactory.getLogger(QaServiceImpl.class);

    // 固定关键参数
    private static final String APP_KEY = "hengnaohH87BFDX0igOEwuxZLIk";
    private static final String APP_SECRET = "ygd8nqxqkq3aqzpsfip1yg95uy4a7xwb";
    private static final String BASE_URL = "https://www.das-ai.com";
    private static final String AGENT_ID = "a50a28d9-fdaa-497d-ae2d-cb4e7c53ca31";

    // 设置为false，使用真实AI服务
    private static final boolean USE_MOCK_DATA = false;

    private final CloseableHttpClient httpClient;
    private final Map<String, List<QaKnowledge>> qaHistory = new HashMap<>();

    public QaServiceImpl() {
        // 设置超时参数
        int timeout = 30000; // 30秒超时
        RequestConfig config = RequestConfig.custom()
            .setConnectTimeout(timeout)
            .setConnectionRequestTimeout(timeout)
            .setSocketTimeout(timeout)
            .build();
        
        httpClient = HttpClients.custom()
            .setDefaultRequestConfig(config)
            .build();
    }

    @Override
    public QaKnowledge getAnswer(String question, String conferenceId) {
        try {
            logger.debug("处理问题: {}, 会议ID: {}", question, conferenceId);
            
            // 直接使用模拟数据或尝试API连接
            String answer;
            if (USE_MOCK_DATA) {
                answer = getMockAnswer(question);
                logger.info("使用模拟数据回答问题: {}", answer);
            } else {
                // 调用AI服务
                String responseBody = callAgent(question);
                logger.debug("AI服务原始响应: {}", responseBody);
                answer = extractAiResponse(responseBody);
                logger.info("AI服务回答: {}", answer);
            }
            
            // 创建并保存问答记录
            QaKnowledge qaKnowledge = new QaKnowledge();
            qaKnowledge.setQuestion(question);
            qaKnowledge.setAnswer(answer);
            qaKnowledge.setConferenceId(conferenceId);
            qaKnowledge.setTimestamp(System.currentTimeMillis());
            
            // 添加到历史记录
            qaHistory.computeIfAbsent(conferenceId, k -> new ArrayList<>()).add(qaKnowledge);
            
            logger.debug("答案生成成功: {}", answer);
            return qaKnowledge;
        } catch (Exception e) {
            logger.error("处理问答失败", e);
            
            QaKnowledge errorResponse = new QaKnowledge();
            errorResponse.setQuestion(question);
            
            // 使用模拟数据作为回退
            String mockAnswer = getMockAnswer(question);
            errorResponse.setAnswer(mockAnswer);
            
            errorResponse.setConferenceId(conferenceId);
            errorResponse.setTimestamp(System.currentTimeMillis());
            return errorResponse;
        }
    }

    /**
     * 生成模拟回答
     * @param question 用户问题
     * @return 模拟的AI回答
     */
    private String getMockAnswer(String question) {
        // 关键词匹配
        String lowerQuestion = question.toLowerCase();
        
        if (lowerQuestion.contains("你是谁") || lowerQuestion.contains("你叫什么")) {
            return "我是西湖论剑数字安全大会的AI助手，很高兴为您服务！";
        }
        
        if (lowerQuestion.contains("时间") || lowerQuestion.contains("几点") || lowerQuestion.contains("日期")) {
            return "西湖论剑数字安全大会将于2024年5月15日至17日在杭州国际博览中心举行，每天上午9:00开始，下午18:00结束。";
        }
        
        if (lowerQuestion.contains("地点") || lowerQuestion.contains("位置") || lowerQuestion.contains("在哪")) {
            return "西湖论剑数字安全大会举办地点在杭州国际博览中心，地址：浙江省杭州市萧山区奔竞大道353号。您可以通过地铁1号线直达，或乘坐公交K705、K196路到达。";
        }
        
        if (lowerQuestion.contains("主题") || lowerQuestion.contains("内容")) {
            return "本次大会主题为\"数字安全，守护未来\"，涵盖网络安全、AI安全、数据安全、物联网安全等多个领域的前沿技术与发展趋势。";
        }
        
        if (lowerQuestion.contains("专家") || lowerQuestion.contains("嘉宾")) {
            return "本次大会邀请了包括钟武、冯登国、王小云等多位院士及行业领军人物，微软、谷歌、阿里巴巴、腾讯等公司的技术专家将分享最新安全技术研究成果。";
        }
        
        if (lowerQuestion.contains("议程") || lowerQuestion.contains("日程") || lowerQuestion.contains("安排")) {
            return "大会日程安排如下：\n第一天：开幕式与主旨演讲\n第二天：技术分论坛与展示\n第三天：专题研讨会与闭幕式\n\n您可以在大会APP上查看详细时间表。";
        }
        
        if (lowerQuestion.contains("注册") || lowerQuestion.contains("报名") || lowerQuestion.contains("门票")) {
            return "您可以通过官网www.westlakesecurity.com完成注册报名。普通票价1200元/人，学生票600元/人（需提供有效学生证），VIP票2400元/人。";
        }
        
        if (lowerQuestion.contains("123") || lowerQuestion.equals("123")) {
            return "您好，这是一个简单的测试回复。我正常工作中！";
        }
        
        if (lowerQuestion.contains("你好") || lowerQuestion.contains("hello") || lowerQuestion.contains("hi")) {
            return "您好！我是西湖论剑数字安全大会的AI助手。有什么可以帮助您的吗？";
        }
        
        // 默认回答
        return "感谢您的提问。西湖论剑数字安全大会是国内领先的网络安全技术交流平台，每年汇聚行业顶尖专家和企业，探讨数字安全前沿技术与发展趋势。如果您有关于大会时间、地点、议程、嘉宾等具体问题，请随时向我咨询。";
    }

    /**
     * 调用AI服务 - 使用HttpURLConnection实现
     * @param userInput 用户输入的问题
     * @return AI服务的原始JSON响应
     */
    private String callAgent(String userInput) {
        int maxRetries = 1; // 减少为1次重试
        int retryCount = 0;
        Exception lastException = null;
        
        while (retryCount < maxRetries) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                // 1. 生成签名
                long timestamp = System.currentTimeMillis();
                String data = timestamp + "\n" + APP_SECRET + "\n" + APP_KEY;
                String sign = timestamp + generateHmacSHA256(data, APP_SECRET);

                logger.debug("请求时间戳: {}", timestamp);
                logger.debug("API请求地址: {}", BASE_URL + "/open/api/v2/agent/execute");
                logger.info("开始调用AI服务，尝试次数: {}", retryCount + 1);
                
                // 2. 构建请求体
                String requestBody = String.format(
                    "{\"id\":\"%s\"," +
                        "\"sid\":\"%s\"," +
                        "\"input\":\"%s\"," +
                        "\"stream\":false," +
                        "\"order\":\"routine\"}",
                    AGENT_ID,
                    UUID.randomUUID().toString(),
                    userInput.replace("\"", "\\\"")
                );
                
                // 3. 创建并配置连接
                URL url = new URL(BASE_URL + "/open/api/v2/agent/execute");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("appKey", APP_KEY);
                connection.setRequestProperty("sign", sign);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                
                // 设置半分钟超时
                connection.setConnectTimeout(30000); // 连接超时30秒
                connection.setReadTimeout(30000);    // 读取超时30秒
                
                // 4. 发送请求体
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] inputBytes = requestBody.getBytes(StandardCharsets.UTF_8);
                    os.write(inputBytes, 0, inputBytes.length);
                }
                
                // 5. 读取响应
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    StringBuilder response = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    String responseContent = response.toString();
                    logger.debug("响应内容: {}", responseContent);
                    return responseContent;
                } else {
                    throw new RuntimeException("AI服务响应错误，状态码：" + responseCode);
                }
            } catch (Exception e) {
                lastException = e;
                logger.error("AI服务调用异常 (尝试 {}/{}): {}", retryCount + 1, maxRetries, e.getMessage());
                retryCount++;
                
                if (retryCount < maxRetries) {
                    // 等待一段时间后重试
                    try {
                        Thread.sleep(500); // 减少等待时间为500毫秒
                        logger.info("等待500毫秒后重试");
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            } finally {
                // 关闭资源
                try {
                    if (reader != null) reader.close();
                    if (connection != null) connection.disconnect();
                } catch (Exception e) {
                    logger.error("关闭资源失败", e);
                }
            }
        }
        
        // 所有重试都失败了
        logger.error("AI服务调用失败，已重试 {} 次", maxRetries, lastException);
        // 返回错误信息JSON
        return "{\"error\":\"AI服务连接失败，已重试 " + maxRetries + " 次: " + (lastException != null ? lastException.getMessage() : "未知错误") + "\"}";
    }
    
    /**
     * 从JSON响应中提取AI回复内容
     * @param jsonResponse JSON响应字符串
     * @return AI回复内容
     */
    private String extractAiResponse(String jsonResponse) {
        try {
            logger.debug("开始解析AI回复内容");
            
            // 检查是否包含错误信息
            if (jsonResponse.contains("\"error\":")) {
                logger.error("API返回错误信息: {}", jsonResponse);
                return "API调用错误: " + jsonResponse;
            }
            
            // 根据ai.md示例，简单查找最后一个assistant消息内容
            int lastAssistant = jsonResponse.lastIndexOf("\"role\":\"assistant\"");
            if (lastAssistant == -1) {
                logger.error("未找到assistant角色回复: {}", jsonResponse);
                return "未找到AI回复";
            }
            
            int contentStart = jsonResponse.indexOf("\"content\":\"", lastAssistant) + 10;
            int contentEnd = jsonResponse.indexOf("\"", contentStart + 1);
            
            if (contentStart < 10 || contentEnd <= contentStart) {
                logger.error("无法确定content内容边界: {}", jsonResponse);
                return "回复内容解析失败";
            }
            
            // 处理转义字符
            String content = jsonResponse.substring(contentStart, contentEnd)
                .replace("\\n", "\n")
                .replace("\\\"", "\"")
                .replace("\\/", "/");
            
            // 清理可能导致问题的特殊字符
            content = content.replaceAll("\\*\\*", "")  // 移除markdown加粗标记
                             .replaceAll("[\\p{C}]", "") // 移除不可见控制字符
                             .trim();
            
            // 移除首尾的引号(如果有)
            if (content.startsWith("\"") && content.endsWith("\"")) {
                content = content.substring(1, content.length() - 1);
            }
            
            // 字数限制，最多2000字
            final int MAX_LENGTH = 2000;
            if (content.length() > MAX_LENGTH) {
                content = content.substring(0, MAX_LENGTH) + "...(内容已截断)";
                logger.warn("AI回答超过{}字，已截断", MAX_LENGTH);
            }
                
            logger.debug("成功解析AI回答: {}", content);
            return content;
        } catch (Exception e) {
            logger.error("解析AI回复异常", e);
            return "解析响应失败: " + e.getMessage();
        }
    }
    
    /**
     * 生成HMAC-SHA256签名
     */
    private String generateHmacSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] hash = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

    @Override
    public List<QaKnowledge> getHistory(String conferenceId) {
        if (conferenceId == null || conferenceId.isEmpty()) {
            // 如果未指定会议ID，返回所有历史记录
            List<QaKnowledge> allHistory = new ArrayList<>();
            qaHistory.values().forEach(allHistory::addAll);
            return allHistory;
        }
        
        // 返回指定会议的历史记录
        return qaHistory.getOrDefault(conferenceId, new ArrayList<>());
    }
}