# 会议助手系统后端服务

## 项目简介
会议助手系统是一个基于Spring Boot的Web应用，提供会议管理、智能问答等功能。系统集成了AI助手，能够智能回答用户关于会议的问题。

## 技术栈
- **核心框架**: Spring Boot 2.7.18
- **安全框架**: Spring Security
- **数据库**: MySQL 8.0
- **ORM框架**: MyBatis-Plus 3.5.3.1
- **连接池**: Druid 1.2.8
- **API文档**: Swagger 3.0.0
- **WebSocket**: Spring WebSocket
- **AI集成**: OpenAI GPT-3 API
- **其他工具**:
  - JWT (JSON Web Token)
  - Lombok
  - Apache Commons Lang3
  - Apache HttpClient

## 主要功能
1. 用户认证与授权
2. 会议管理（创建、编辑、删除、查询）
3. 智能问答系统
4. WebSocket实时通信
5. 文件上传与管理
6. 邮件通知

## 环境要求
- JDK 8+
- Maven 3.6+
- MySQL 8.0+
- 内存: 4GB+
- 磁盘空间: 1GB+

## 配置说明
### 数据库配置
```yaml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/ai_helper
    username: root
    password: 123456
```

### AI服务配置
```yaml
ai:
  app:
    key: your-app-key
    secret: your-app-secret
  service:
    url: https://www.das-ai.com
  agent:
    id: your-agent-id
```

### 服务器配置
```yaml
server:
  port: 8081
```

## 快速开始

### 1. 克隆项目
```bash
git clone [项目地址]
cd helperserver
```

### 2. 配置数据库
- 创建MySQL数据库：ai_helper
- 执行数据库初始化脚本（位于 src/main/resources/db/init.sql）

### 3. 修改配置
- 复制 application.yml.example 为 application.yml
- 修改数据库连接信息
- 配置AI服务参数

### 4. 编译运行
```bash
mvn clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

### 5. 访问服务
- API文档：http://localhost:8081/swagger-ui.html
- 服务地址：http://localhost:8081

## 项目结构
```
helperserver/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/helperserver/
│   │   │       ├── config/      # 配置类
│   │   │       ├── controller/  # 控制器
│   │   │       ├── service/     # 服务层
│   │   │       ├── mapper/      # MyBatis映射
│   │   │       ├── bean/        # 实体类
│   │   │       └── util/        # 工具类
│   │   └── resources/
│   │       ├── mapper/          # MyBatis XML
│   │       ├── application.yml  # 配置文件
│   │       └── db/             # 数据库脚本
│   └── test/                    # 测试代码
├── pom.xml                      # Maven配置
└── README.md                    # 项目说明
```

## API文档
启动服务后访问 Swagger UI：http://localhost:8081/swagger-ui.html

## 日志配置
- 日志文件位置：logs/application.log
- 日志级别：
  - ROOT: INFO
  - com.helperserver: DEBUG

## 注意事项
1. 确保MySQL服务已启动且配置正确
2. 检查AI服务配置是否正确
3. 确保所需端口（8081）未被占用
4. 建议在生产环境中修改默认密码

## 常见问题
1. 数据库连接失败
   - 检查MySQL服务是否启动
   - 验证数据库用户名密码是否正确
   - 确认数据库名称是否存在

2. AI服务调用失败
   - 检查网络连接
   - 验证API密钥是否正确
   - 确认服务URL是否可访问

## 贡献指南
1. Fork 项目
2. 创建特性分支
3. 提交更改
4. 推送到分支
5. 创建 Pull Request

## 许可证
[许可证类型]