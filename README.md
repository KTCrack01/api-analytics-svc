# api-analytics-svc

메시징 전송 로그를 집계하고 대시보드용 지표를 제공하는 Analytics 서비스입니다. 전송 건수 월별 집계, 상태별 통계, 상위 수신번호 랭킹 등의 API를 제공합니다.

---

## 기술 스택 및 실행 포트
- Spring Boot 3, Java 21, Gradle
- JPA + PostgreSQL
- 기본 컨테이너 포트: `8080` ([ADR-005](../msa-project-hub/docs/adr/ADR-005-service-port-convention.md))

---

## 환경 변수
- `DATABASE_URL` (예: `jdbc:postgresql://localhost:5432/analytics?currentSchema=api_analytics_svc`)
- `DATABASE_USERNAME` (기본값: `postgres`)
- `DATABASE_PASSWORD` (기본값: `password`)

application.properties 발췌:

```properties
spring.datasource.url=${DATABASE_URL:jdbc:postgresql://localhost:5432/analytics?currentSchema=api_analytics_svc}
spring.datasource.username=${DATABASE_USERNAME:postgres}
spring.datasource.password=${DATABASE_PASSWORD:password}
spring.jpa.properties.hibernate.default_schema=api_analytics_svc
spring.jpa.hibernate.ddl-auto=none
```

---

## 빌드 및 실행

```bash
# 로컬 실행
./gradlew bootRun

# 빌드(JAR)
./gradlew bootJar

# Docker 이미지 빌드
docker build -t api-analytics-svc:local .

# Docker 컨테이너 실행 (예시)
docker run --rm -p 8080:8080 \
  -e DATABASE_URL="jdbc:postgresql://host.docker.internal:5432/analytics?currentSchema=api_analytics_svc" \
  -e DATABASE_USERNAME=postgres \
  -e DATABASE_PASSWORD=password \
  api-analytics-svc:local
```

---

## CORS
허용 오리진과 메서드는 `WebConfig`에서 관리됩니다. 기본 허용 오리진에는 `http://localhost:3000`과 배포된 프론트엔드 주소가 포함됩니다.

---

## API 엔드포인트
베이스 경로: `/api/v1/dashboard/data`

- 생성: `POST /api/v1/dashboard/data`
  - Body 예시
  ```json
  {
    "userEmail": "user@example.com",
    "phoneNum": "+821012345678",
    "sendAt": "2025-01-01 13:00:00",
    "status": "queued",
    "providerSid": "SMxxxxxxxx"
  }
  ```
  - 응답: 201 Created

- 상태 갱신: `POST /api/v1/dashboard/data/status`
  - Body 예시
  ```json
  { "providerSid": "SMxxxxxxxx", "status": "delivered" }
  ```
  - 응답: 200 OK

- 월별 전송 건수: `GET /api/v1/dashboard/data/monthly-counts?userEmail=...&year=2025`
  - 응답 예시
  ```json
  { "userEmail": "user@example.com", "year": 2025, "counts": [0,1,3, ...] }
  ```

- 상태별 월간 통계: `GET /api/v1/dashboard/data/status-monthly-counts?userEmail=...&year=2025&month=1`
  - 응답 예시
  ```json
  { "year": 2025, "month": 1, "delivered": 10, "failed": 2 }
  ```

- 상위 수신번호 랭킹: `GET /api/v1/dashboard/data/phone-num-ranking?userEmail=...&limit=10`
  - 응답 예시
  ```json
  [ { "phoneNum": "+821012345678", "count": 12 } ]
  ```

---

## 참고
- 포트 정책: [ADR-005](../msa-project-hub/docs/adr/ADR-005-service-port-convention.md) (Service Port Convention)
- 데이터 분리: [ADR-004](../msa-project-hub/docs/adr/ADR-004-database-per-service.md) (Database Per Service)
- API 버저닝: [ADR-003](../msa-project-hub/docs/adr/ADR-003-api-versioning-and-base-path.md)
