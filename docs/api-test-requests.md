# API Test Requests

## Summary API

```bash
curl -X POST "http://localhost:8080/api/v1/ai/summary" ^
  -H "Content-Type: application/json" ^
  -d "{\"text\":\"Spring Boot helps teams build APIs quickly. It includes auto-configuration and starter dependencies. This MVP focuses on interview delivery.\",\"maxSentences\":2}"
```

## QA API

```bash
curl -X POST "http://localhost:8080/api/v1/ai/qa" ^
  -H "Content-Type: application/json" ^
  -d "{\"document\":\"The project provides text summary and document QA endpoints. It also includes logging, validation, and exception handling. Swagger UI is enabled for demo.\",\"question\":\"Which AI capabilities does the project provide?\"}"
```
