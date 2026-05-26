# Reproducible Build Path

This file is the single source of truth for rerunning the project in a clean environment.

## Preferred Path A: JDK 17 + Maven Wrapper

Use this path when Docker is unavailable.

### Preconditions

- JDK 17 must be installed
- `java -version` should show version 17
- `javac -version` should work
- Maven Wrapper downloads Maven into repository-local `.mvn-home/`

### Windows Commands

```powershell
java -version
javac -version
.\mvnw.cmd -v
.\mvnw.cmd clean test
.\mvnw.cmd -DskipTests clean package
java -jar target\ai-capability-backend-0.0.1-SNAPSHOT.jar
```

### Minimal Test Command

```powershell
.\mvnw.cmd clean test
```

## Preferred Path B: Docker

Use this path when the host Maven or Java chain is unreliable.

### Preconditions

- Docker daemon is running

### Commands

```bash
docker build --progress=plain -t ai-capability-backend-mvp .
docker run --rm -p 8080:8080 ai-capability-backend-mvp
```

### Minimal Test Command

```bash
docker build --progress=plain -t ai-capability-backend-mvp .
```

## Smoke Test

After the service starts:

```bash
curl -X POST "http://localhost:8080/api/v1/ai/summary" \
  -H "Content-Type: application/json" \
  -d "{\"text\":\"Spring Boot helps teams build APIs quickly. It includes auto-configuration and starter dependencies. This MVP focuses on interview delivery.\",\"maxSentences\":2}"
```

Expected result: HTTP 200 with `success: true`.

## Success Logs

- `jdk17-build-success.log`
- `docker-build-success.log`
- `docker-run-success.log`
