# AI Capability Backend MVP

Spring Boot backend MVP for the interview task. It provides two AI-style APIs with structured JSON output:

- `POST /api/v1/ai/summary` for text summarization
- `POST /api/v1/ai/qa` for document question answering

The project includes:

- structured API response model
- request validation
- global exception handling
- request trace logging
- Swagger/OpenAPI docs
- controller integration tests
- runnable local and Docker instructions
- API test examples and screenshots

## 1. Tech Stack

- Java 17
- Spring Boot 3.3.5
- Maven / Maven Wrapper
- Swagger UI via springdoc
- JUnit 5 + MockMvc
- Docker

## 2. Project Structure

```text
.
|-- .mvn/
|-- docs/
|-- src/
|-- .dockerignore
|-- .gitignore
|-- Dockerfile
|-- mvnw
|-- mvnw.cmd
|-- pom.xml
`-- README.md
```

## 3. Stable Reproduction Path

For a clean environment, use one of these two paths only:

### Path A: JDK 17 + Maven Wrapper

Use this if the machine has JDK 17 and `javac`. The wrapper now stores its Maven distribution in project-local `.mvn-home/`, so it does not depend on a writable user home cache.

#### Minimal verification

```powershell
java -version
javac -version
.\mvnw.cmd clean test
```

If `javac` is missing, the machine is pointing to a JRE instead of a JDK and the build will fail.

#### Full build and run

```powershell
.\mvnw.cmd -DskipTests clean package
java -jar target\ai-capability-backend-0.0.1-SNAPSHOT.jar
```

### Path B: Docker

Use this if the host Maven chain is not reliable.

#### Minimal verification

```bash
docker build --progress=plain -t ai-capability-backend-mvp .
```

#### Full build and run

```bash
docker build --progress=plain -t ai-capability-backend-mvp .
docker run --rm -p 8080:8080 ai-capability-backend-mvp
```

Detailed rerun notes are in [docs/reproducible-build.md](docs/reproducible-build.md).

## 4. Quick Start

### Option A: Run with Maven Wrapper

This is the recommended local path because it does not rely on a preinstalled Maven version and it keeps Maven files inside the repository workspace.

#### Windows

```powershell
.\mvnw.cmd clean test
.\mvnw.cmd clean package
java -jar target\ai-capability-backend-0.0.1-SNAPSHOT.jar
```

#### macOS / Linux

```bash
chmod +x mvnw
./mvnw clean test
./mvnw clean package
java -jar target/ai-capability-backend-0.0.1-SNAPSHOT.jar
```

After startup:

- Swagger UI: `http://localhost:8080/swagger-ui.html`

### Option B: Run with Docker

Build image:

```bash
docker build -t ai-capability-backend-mvp .
```

Run container:

```bash
docker run --rm -p 8080:8080 ai-capability-backend-mvp
```

After startup:

- Swagger UI: `http://localhost:8080/swagger-ui.html`

## 5. Build and Test Notes

### Minimum environment

- Java 17
- network access to download Maven dependencies the first time
- for local builds, `javac` must be available

### Verified commands

Local test:

```bash
./mvnw clean test
```

Local package:

```bash
./mvnw clean package
```

Docker build:

```bash
docker build -t ai-capability-backend-mvp .
```

## 6. API Endpoints

### 6.1 Summary API

`POST /api/v1/ai/summary`

Request:

```json
{
  "text": "Spring Boot helps teams build APIs quickly. It includes auto-configuration and starter dependencies. This MVP focuses on interview delivery.",
  "maxSentences": 2
}
```

Response:

```json
{
  "success": true,
  "data": {
    "summary": "Spring Boot helps teams build APIs quickly. It includes auto-configuration and starter dependencies.",
    "sentenceCount": 2,
    "keywords": [
      "apis",
      "auto",
      "boot",
      "build",
      "configuration"
    ]
  },
  "traceId": "3de938db-d85e-4217-ac05-62f8e4b8dcef"
}
```

### 6.2 QA API

`POST /api/v1/ai/qa`

Request:

```json
{
  "document": "The project provides text summary and document QA endpoints. It also includes logging, validation, and exception handling. Swagger UI is enabled for demo.",
  "question": "Which AI capabilities does the project provide?"
}
```

Response:

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
  "traceId": "8aff384c-ef17-45ed-9e51-df4b569c09fa"
}
```

## 7. API Test Samples

### cURL examples

Summary:

```bash
curl -X POST "http://localhost:8080/api/v1/ai/summary" \
  -H "Content-Type: application/json" \
  -d "{\"text\":\"Spring Boot helps teams build APIs quickly. It includes auto-configuration and starter dependencies. This MVP focuses on interview delivery.\",\"maxSentences\":2}"
```

QA:

```bash
curl -X POST "http://localhost:8080/api/v1/ai/qa" \
  -H "Content-Type: application/json" \
  -d "{\"document\":\"The project provides text summary and document QA endpoints. It also includes logging, validation, and exception handling. Swagger UI is enabled for demo.\",\"question\":\"Which AI capabilities does the project provide?\"}"
```

Windows PowerShell examples are also listed in [docs/api-test-requests.md](docs/api-test-requests.md).

## 8. Runtime and Test Artifacts

- [Swagger screenshot](docs/api-test-screenshot-swagger.png)
- [Summary response screenshot](docs/api-test-screenshot-summary.png)
- [QA response screenshot](docs/api-test-screenshot-qa.png)
- [Maven wrapper test screenshot](docs/run-test-screenshot-mvnw-test.png)
- [Docker run screenshot](docs/run-test-screenshot-docker.png)
- [JDK17 build success log](docs/jdk17-build-success.log)
- [Docker build success log](docs/docker-build-success.log)
- [Docker run success log](docs/docker-run-success.log)

Raw sample responses:

- [summary-response.json](docs/summary-response.json)
- [qa-response.json](docs/qa-response.json)
- [docker-summary-response.json](docs/docker-summary-response.json)
- [docker-qa-response.json](docs/docker-qa-response.json)

## 9. Engineering Notes

- Unified response structure via `ApiResponse<T>`
- Global error handling via `GlobalExceptionHandler`
- Request trace ID logging via `RequestTraceFilter`
- Swagger UI for quick manual verification
- Local controller tests via `MockMvc`

## 10. Additional Delivery Files

- [AI collaboration note](docs/ai-collaboration.md)
- [Debugging record](docs/debugging-record.md)

## 11. Reviewer Shortcut

If you want the fastest verification path:

```bash
./mvnw clean test
./mvnw clean package
java -jar target/ai-capability-backend-0.0.1-SNAPSHOT.jar
```

Or:

```bash
docker build -t ai-capability-backend-mvp .
docker run --rm -p 8080:8080 ai-capability-backend-mvp
```
