# AI Capability Backend MVP

一个面向面试题的 Spring Boot 后端 MVP，提供两个“AI 能力”接口：

- 文本摘要：`POST /api/v1/ai/summary`
- 资料问答：`POST /api/v1/ai/qa`

项目强调可运行、可演示、可解释，包含结构化 JSON 返回、统一异常处理、请求链路日志、Swagger 文档、接口测试和交付说明。

## 1. 技术栈

- Java 17
- Spring Boot 3.3.5
- Spring Web
- Spring Validation
- springdoc OpenAPI / Swagger UI
- JUnit 5 + MockMvc

## 2. 项目结构

```text
.
├── docs
│   ├── api-test-requests.md
│   ├── api-test-screenshot-swagger.png
│   ├── api-test-screenshot-summary.png
│   ├── api-test-screenshot-qa.png
│   ├── ai-collaboration.md
│   └── debugging-record.md
├── src
│   ├── main
│   │   ├── java/com/example/aibackend
│   │   │   ├── api
│   │   │   ├── config
│   │   │   ├── exception
│   │   │   └── service
│   │   └── resources/application.yml
│   └── test/java/com/example/aibackend/AiControllerTest.java
├── pom.xml
└── README.md
```

## 3. 运行方式

### 3.1 启动项目

推荐方式：

```bash
mvn clean package
java -jar target/ai-capability-backend-0.0.1-SNAPSHOT.jar
```

启动后访问：

- Swagger UI: `http://localhost:8080/swagger-ui.html`

也可以尝试：

```bash
mvn spring-boot:run
```

但在当前中文目录环境下，`spring-boot:run` 可能出现类加载异常，因此这里将 `java -jar` 作为更稳妥的演示方式。

### 3.2 运行测试

```bash
mvn test
```

## 4. 接口说明

### 4.1 文本摘要

`POST /api/v1/ai/summary`

请求示例：

```json
{
  "text": "Spring Boot helps teams build APIs quickly. It includes auto-configuration and starter dependencies. This MVP focuses on interview delivery.",
  "maxSentences": 2
}
```

返回示例：

```json
{
  "success": true,
  "data": {
    "summary": "Spring Boot helps teams build APIs quickly. It includes auto-configuration and starter dependencies.",
    "sentenceCount": 2,
    "keywords": ["api", "auto", "boot", "build", "configuration"]
  },
  "traceId": "..."
}
```

### 4.2 资料问答

`POST /api/v1/ai/qa`

请求示例：

```json
{
  "document": "The project provides text summary and document QA endpoints. It also includes logging, validation, and exception handling. Swagger UI is enabled for demo.",
  "question": "Which AI capabilities does the project provide?"
}
```

返回示例：

```json
{
  "success": true,
  "data": {
    "answer": "Based on the provided material, the most relevant answer is: The project provides text summary and document QA endpoints.",
    "evidence": [
      "The project provides text summary and document QA endpoints.",
      "It also includes logging, validation, and exception handling."
    ],
    "confidence": "high"
  },
  "traceId": "..."
}
```

## 5. 工程特性

- 统一返回结构：`ApiResponse<T>`
- 统一异常处理：`GlobalExceptionHandler`
- 参数校验：`jakarta.validation`
- 请求日志与链路 ID：`RequestTraceFilter`
- Swagger 文档：便于面试演示

## 6. 交付材料

- 核心目录：当前项目根目录
- README：本文件
- 接口测试截图：
  - [Swagger 截图](C:/Users/Administrator/Desktop/AI面试/docs/api-test-screenshot-swagger.png)
  - [Summary 响应截图](C:/Users/Administrator/Desktop/AI面试/docs/api-test-screenshot-summary.png)
  - [QA 响应截图](C:/Users/Administrator/Desktop/AI面试/docs/api-test-screenshot-qa.png)
- AI 协作说明：见 [ai-collaboration.md](C:/Users/Administrator/Desktop/AI面试/docs/ai-collaboration.md)
- 真实排错记录：见 [debugging-record.md](C:/Users/Administrator/Desktop/AI面试/docs/debugging-record.md)

## 7. 验证结果

- `mvn test`：通过，3/3 成功
- 本地接口联调：已完成
- Swagger 页面：已生成截图
- 请求日志：已在 `app.log` 中验证

## 8. 说明

这里的“AI 能力”MVP 采用规则增强的轻量实现，目的是在不依赖外部模型密钥的情况下，完整展示后端接口设计、工程结构、错误处理、日志和可测试性。如果后续技术沟通需要，我可以继续把它升级为接入大模型 API 的版本。
