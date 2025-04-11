

```java
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;
import java.util.UUID;

public class AgentServiceClient {

    private static final String APP_KEY = "hengnaohH87BFDX0igOEwuxZLIk";
    private static final String APP_SECRET = "ygd8nqxqkq3aqzpsfip1yg95uy4a7xwb";
    private static final String BASE_URL = "https://www.das-ai.com";
    private static final String AGENT_ID = "a50a28d9-fdaa-497d-ae2d-cb4e7c53ca31";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, "UTF-8");

        System.out.println("请输入您的问题（输入exit退出）:");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input.trim())) {
                break;
            }

            String response = callAgent(input);
            System.out.println("完整响应:\n" + response);

            String aiResponse = extractAiResponse(response);
            System.out.println("\nAI回复:\n" + aiResponse + "\n");
        }

        scanner.close();
    }

    public static String callAgent(String userInput) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            // 1. 生成签名
            long timestamp = System.currentTimeMillis();
            String data = timestamp + "\n" + APP_SECRET + "\n" + APP_KEY;
            String sign = timestamp + generateHmacSHA256(data, APP_SECRET);

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
                return response.toString();
            } else {
                return "{\"error\":\"HTTP error code: " + responseCode + "\"}";
            }

        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        } finally {
            try {
                if (reader != null) reader.close();
                if (connection != null) connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String generateHmacSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] hash = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

    private static String extractAiResponse(String jsonResponse) {
        try {
            // 简单查找最后一个assistant消息内容
            int lastAssistant = jsonResponse.lastIndexOf("\"role\":\"assistant\"");
            if (lastAssistant == -1) return "未找到AI回复";

            int contentStart = jsonResponse.indexOf("\"content\":\"", lastAssistant) + 10;
            int contentEnd = jsonResponse.indexOf("\"", contentStart + 1);

            if (contentStart < 10 || contentEnd <= contentStart) {
                return "回复内容解析失败";
            }

            // 处理转义字符
            return jsonResponse.substring(contentStart, contentEnd)
                    .replace("\\n", "\n")
                    .replace("\\\"", "\"")
                    .replace("\\/", "/");
        } catch (Exception e) {
            return "解析响应失败: " + e.getMessage();
        }
    }
}
```
完整响应:
{"msg":"恭喜您，操作成功","code":0,"flag":0,"data":{"token_usage":{"completion_tokens":84,"prompt_tokens":2623,"completion_tokens_details":{"reasoning_tokens":0},"total_tokens":2707},"session":{"messages":[{"role":"user","content":"你是谁"},{"role":"assistant","content":"我是2025西湖论剑大会的线上智能参会助手，基于安恒信息恒脑大模型的人工智能技术，为参会者提供全方位的服务。您可以向我咨询大会的相关信息，包括日程安排、嘉宾介绍、成果发布等，并且我还能为您推送大会的最新动态和个性化推荐，确保您的参会体验更加丰富和便捷。有什么我可以帮您的吗？"}],"id":"eeaa59a5-1415-49dc-be8c-20791ddb1dbd"},"results":{}},"tid":"e089a08c57a34776aa82c214411478e5.532953.17440787054139521"}

AI回复:
"我是2025西湖论剑大会的线上智能参会助手，基于安恒信息恒脑大模型的人工智能技术，为参会者提供全方位的服务。您可以向我咨询大会的相关信息，包括日程安排、嘉宾介绍、成果发布等，并且我还能为您推送大会的最新动态和个性化推荐，确保您的参会体验更加丰富和便捷。有什么我可以帮您的吗？



