# 真实排错记录

## 问题

本地准备演示环境时，`mvn spring-boot:run` 启动失败，报错如下：

```text
Error: Could not find or load main class com.example.aibackend.AiCapabilityBackendApplication
Caused by: java.lang.ClassNotFoundException: com.example.aibackend.AiCapabilityBackendApplication
```

## 排查过程

1. 先检查源码目录和 `target/classes`，确认主类 `.class` 文件实际已经成功编译出来。
2. 再查看打包日志，发现 Maven 输出路径在控制台里出现了中文目录名的异常显示。
3. 结合现象判断：问题不在源码本身，而在 `spring-boot:run` 结合当前中文路径环境时的类加载行为。

## 处理方式

1. 先执行 `mvn clean package`，确认可执行 jar 正常生成。
2. 改用 `java -jar target/ai-capability-backend-0.0.1-SNAPSHOT.jar` 启动服务。
3. 再通过实际接口请求和 Swagger 页面验证，确认应用运行正常。

## 结论

这个问题说明：在 Windows + 中文目录场景下，演示型项目最好准备一条更稳妥的启动路径。对于这次面试交付，`java -jar` 比 `spring-boot:run` 更可靠，也更适合发给面试官复现。
