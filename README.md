# Findex

Findex는 금융 지수 정보와 일자별 지수 데이터를 관리하고, 공공데이터포털 금융위원회 지수 API를 통해 데이터를 동기화하는 Spring Boot 기반 서비스입니다.

지수 정보 CRUD, 지수 데이터 조회와 CSV 다운로드, 성과 차트, 수동/자동 동기화, 동기화 이력 조회 기능을 제공합니다. 정적 프론트엔드 파일도 포함되어 있어 애플리케이션 실행 후 브라우저에서 화면을 확인할 수 있습니다.

## 주요 기능

- 지수 정보 등록, 조회, 수정, 삭제
- 지수 데이터 등록, 조회, 수정, 삭제
- 커서 기반 페이지네이션과 정렬/필터 검색
- 지수 데이터 CSV 다운로드
- 관심 지수 성과 조회
- 지수별 차트 데이터 조회
- 기간별 지수 성과 순위 조회
- 공공데이터포털 금융위원회 지수 API 연동
- 지수 정보 및 지수 데이터 수동 동기화
- 스케줄러 기반 자동 동기화
- 자동 동기화 설정 조회 및 변경
- 동기화 작업 이력 조회
- Swagger UI 기반 API 문서 제공

## 기술 스택

- Java 17
- Spring Boot 3.5.16
- Gradle
- Spring Web
- Spring Data JPA
- Querydsl
- PostgreSQL
- H2 Database
- Flyway
- springdoc-openapi
- JUnit 5
- Testcontainers

## 프로젝트 구조

```text
src/main/java/com/sb13/findex
├── autosyncconfig  # 자동 동기화 설정, 스케줄러
├── externalapi     # 공공데이터포털 API 연동, 요청/응답 DTO, 전용 설정
├── global          # 공통 설정, 공통 엔티티, 공통 예외 처리
├── indexinfo       # 지수 정보 도메인
├── indexdata       # 지수 데이터 및 대시보드 도메인
└── sync            # 수동 동기화 유스케이스, 동기화 작업 이력

src/main/resources
├── application.yaml
├── application-prod.yml
├── application-local.yaml.txt
├── application-local-h2.yml.txt
├── db/migration
└── static
```

## 사전 준비

- Java 17
- PostgreSQL 또는 H2
- 공공데이터포털 `DATA_GO_KR_SERVICE_KEY`

공공데이터포털 인증키는 Decoding 키를 사용합니다. 자세한 내용은 [docs/data-go-kr-service-key-guide.md](docs/data-go-kr-service-key-guide.md)를 참고하세요.

## 로컬 실행

### 1. 로컬 데이터베이스 준비

PostgreSQL을 사용할 경우 로컬 환경에 데이터베이스를 준비합니다. 데이터베이스 이름, 계정, 비밀번호는 개인 개발 환경에 맞게 설정하면 됩니다.

```text
host: localhost
port: 5432
database: findex
username: findex
password: findex1234
```

PostgreSQL을 준비하지 않고 빠르게 실행하려면 H2 프로필을 사용할 수 있습니다.

### 2. 로컬 설정 파일 준비

PostgreSQL로 실행하려면 예시 설정 파일을 복사합니다.

```powershell
Copy-Item src/main/resources/application-local.yaml.txt src/main/resources/application-local.yaml
```

macOS/Linux 또는 Git Bash에서는 다음 명령을 사용할 수 있습니다.

```bash
cp src/main/resources/application-local.yaml.txt src/main/resources/application-local.yaml
```

PostgreSQL을 사용할 경우 `application-local.yaml`의 datasource 정보를 로컬 DB 설정에 맞게 수정합니다.

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/findex
    username: findex
    password: findex1234
```

H2 메모리 DB로 실행하려면 `application-local-h2.yml.txt`를 `application-local-h2.yml`로 복사한 뒤 `local-h2` 프로필을 사용합니다.

```powershell
Copy-Item src/main/resources/application-local-h2.yml.txt src/main/resources/application-local-h2.yml
```

### 3. 환경 변수 설정

Windows PowerShell:

```powershell
$env:SPRING_PROFILES_ACTIVE="local"
$env:DATA_GO_KR_SERVICE_KEY="공공데이터포털_Decoding_인증키"
```

macOS/Linux 또는 Git Bash:

```bash
export SPRING_PROFILES_ACTIVE=local
export DATA_GO_KR_SERVICE_KEY="공공데이터포털_Decoding_인증키"
```

H2로 실행할 때는 `SPRING_PROFILES_ACTIVE` 값을 `local-h2`로 설정합니다.

### 4. 애플리케이션 실행

Windows:

```powershell
.\gradlew.bat bootRun
```

macOS/Linux:

```bash
./gradlew bootRun
```

기본 포트는 `8080`입니다. 로컬 설정 파일의 `server.port`를 변경했다면 해당 포트로 접속하세요.

- 애플리케이션: <http://localhost:8080>
- Swagger UI: <http://localhost:8080/swagger-ui/index.html>
- OpenAPI JSON: <http://localhost:8080/v3/api-docs>

## 테스트 및 빌드

테스트 실행:

```bash
./gradlew test
```

Windows PowerShell:

```powershell
.\gradlew.bat test
```

빌드:

```bash
./gradlew clean build
```

Windows PowerShell:

```powershell
.\gradlew.bat clean build
```

## 주요 API

| 기능 | Method | Path |
| --- | --- | --- |
| 지수 정보 등록 | `POST` | `/api/index-infos` |
| 지수 정보 단건 조회 | `GET` | `/api/index-infos/{id}` |
| 지수 정보 목록 조회 | `GET` | `/api/index-infos` |
| 지수 정보 요약 조회 | `GET` | `/api/index-infos/summaries` |
| 지수 정보 수정 | `PATCH` | `/api/index-infos/{id}` |
| 지수 정보 삭제 | `DELETE` | `/api/index-infos/{id}` |
| 지수 데이터 등록 | `POST` | `/api/index-data` |
| 지수 데이터 목록 조회 | `GET` | `/api/index-data` |
| 지수 데이터 수정 | `PATCH` | `/api/index-data/{id}` |
| 지수 데이터 삭제 | `DELETE` | `/api/index-data/{id}` |
| 지수 데이터 CSV 다운로드 | `GET` | `/api/index-data/export/csv` |
| 관심 지수 성과 조회 | `GET` | `/api/index-data/performance/favorite` |
| 지수 차트 조회 | `GET` | `/api/index-data/{id}/chart` |
| 지수 성과 순위 조회 | `GET` | `/api/index-data/performance/rank` |
| 동기화 이력 조회 | `GET` | `/api/sync-jobs` |
| 지수 정보 수동 동기화 | `POST` | `/api/sync-jobs/index-infos` |
| 지수 데이터 수동 동기화 | `POST` | `/api/sync-jobs/index-data` |
| 자동 동기화 설정 조회 | `GET` | `/api/auto-sync-configs` |
| 자동 동기화 설정 변경 | `PATCH` | `/api/auto-sync-configs/{id}` |

요청/응답 스키마와 상세 파라미터는 Swagger UI에서 확인하는 것을 권장합니다.

## 환경 변수

| 변수 | 설명 | 기본값/예시 |
| --- | --- | --- |
| `SPRING_PROFILES_ACTIVE` | 실행 프로필 | `local`, `local-h2`, `prod` |
| `DATA_GO_KR_SERVICE_KEY` | 공공데이터포털 Decoding 인증키 | 직접 설정 |
| `AUTO_SYNC_CRON` | 자동 동기화 cron 표현식 | `0 0 1 * * *` |
| `AUTO_SYNC_ZONE` | 자동 동기화 기준 시간대 | `Asia/Seoul` |
| `PORT` | 운영 환경 서버 포트 | `8080` |
| `DB_HOST` | 운영 DB 호스트 | Railway PostgreSQL 변수 |
| `DB_PORT` | 운영 DB 포트 | Railway PostgreSQL 변수 |
| `DB_NAME` | 운영 DB 이름 | Railway PostgreSQL 변수 |
| `DB_USERNAME` | 운영 DB 사용자 | Railway PostgreSQL 변수 |
| `DB_PASSWORD` | 운영 DB 비밀번호 | Railway PostgreSQL 변수 |

`.env.example`은 배포 환경 변수 예시를 공유하기 위한 템플릿입니다. 실제 비밀번호나 API Key는 저장소에 커밋하지 말고 실행 환경 변수로 관리하세요.

## 데이터베이스 마이그레이션

프로젝트는 Flyway를 사용해 데이터베이스 스키마를 관리합니다.

- 마이그레이션 파일 위치: `src/main/resources/db/migration`
- 기본 설정: `spring.flyway.enabled=true`
- 기본 JPA DDL 전략: `validate`

운영 및 PostgreSQL 로컬 환경에서는 Flyway 마이그레이션이 적용됩니다. H2 예시 설정은 빠른 로컬 확인을 위해 Flyway를 비활성화하고 `create-drop`을 사용합니다.

## 배포

Railway 배포 환경에서는 `prod` 프로필을 사용합니다.

```text
SPRING_PROFILES_ACTIVE=prod
```

운영 DB 연결 정보와 공공데이터포털 인증키는 Railway 서비스의 Variables에 등록해야 합니다. 자세한 절차는 [docs/railway-deployment.md](docs/railway-deployment.md)를 참고하세요.

## 참고 문서

- [공공데이터포털 Service Key 설정 가이드](docs/data-go-kr-service-key-guide.md)
- [Railway 배포 환경 설정](docs/railway-deployment.md)
- [Flyway 마이그레이션 가이드](docs/flyway-migration-guide.md)
- [Querydsl 설정 가이드](docs/querydsl-setting-guide.md)
- [GitHub Actions PR Target 가이드](docs/github-actions-pr-target-guide.md)
- [Git 커밋 컨벤션](docs/git-commit-convention.md)
