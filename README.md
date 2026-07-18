# Findex

## 팀 정보

- 팀 이름: 코드 한 스푼
- 팀 협업 문서: [Notion](https://app.notion.com/p/397fb16466268061a812ee3324368b9f)

| 이름 | GitHub |
| --- | --- |
| 정구영 | [KooYeoung](https://github.com/KooYeoung) |
| 강다연 | [K-Dayeon03](https://github.com/K-Dayeon03) |
| 유하정 | [hj-log](https://github.com/hj-log) |
| 김두호 | [dooho9767](https://github.com/dooho9767) |
| 김도형 | [DHK777](https://github.com/DHK777) |
| 박경석 | [seok-dev](https://github.com/seok-dev) |

## 프로젝트 소개

Findex는 금융 지수 정보와 일자별 지수 데이터를 관리하고, 공공데이터포털 금융위원회 지수 API를 통해 데이터를 동기화하는 Spring Boot 기반 서비스입니다.

사용자는 지수 정보와 지수 데이터를 등록, 조회, 수정, 삭제할 수 있으며 기간별 성과와 차트 데이터를 통해 지수 흐름을 확인할 수 있습니다. 또한 수동 동기화와 스케줄러 기반 자동 동기화를 지원하고, 각 동기화 작업의 성공/실패 이력을 기록해 데이터 수집 과정을 추적할 수 있습니다.

정적 프론트엔드 파일도 포함되어 있어 애플리케이션 실행 후 브라우저에서 주요 화면을 확인할 수 있습니다.

- 프로젝트 기간: 2026.07.08 ~ 2026.07.21
- 구현 홈페이지: [Findex Dashboard](https://findex-app-production.up.railway.app/#/dashboard)

## 팀원별 구현 기능 상세

| 팀원 | 주요 담당 영역 | 상세 문서 |
| --- | --- | --- |
| 정구영 | 외부 API 연동, 수동 동기화 흐름, 동기화 이력 저장, 프로젝트 통합 | [상세 보기](docs/team/koo-yeoung.md) |
| 강다연 | 대시보드 API, 지수 데이터 조회/통계 기능, 테스트 및 로컬 설정 | [상세 보기](docs/team/k-dayeon03.md) |
| 유하정 | 지수 정보 도메인, 지수 정보 Open API 저장/갱신, 도메인 예외 처리 | [상세 보기](docs/team/hj-log.md) |
| 김두호 | 자동 동기화 설정, 자동 동기화 대상 조회, 스케줄러 연동 | [상세 보기](docs/team/dooho9767.md) |
| 김도형 | 지수 데이터 도메인, 지수 데이터 생성/수정/삭제, Swagger 문서화 | [상세 보기](docs/team/dhk777.md) |
| 박경석 | 동기화 작업 이력 조회, 검색/정렬/커서 페이지네이션, 성능 개선 | [상세 보기](docs/team/seok-dev.md) |

팀원별 상세 구현 내용과 이미지/GIF 첨부 영역은 [팀원별 구현 기능 상세 문서](docs/team-implementation-details.md)에서 확인할 수 있습니다.

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

## 패키지 구조

```text
src
┣ main
┃ ┣ java
┃ ┃ ┗ com
┃ ┃   ┗ sb13
┃ ┃     ┗ findex
┃ ┃       ┣ autosyncconfig
┃ ┃       ┃ ┣ controller
┃ ┃       ┃ ┃ ┗ swagger
┃ ┃       ┃ ┣ dto
┃ ┃       ┃ ┃ ┣ command
┃ ┃       ┃ ┃ ┣ condition
┃ ┃       ┃ ┃ ┣ projection
┃ ┃       ┃ ┃ ┣ request
┃ ┃       ┃ ┃ ┗ response
┃ ┃       ┃ ┣ entity
┃ ┃       ┃ ┣ repository
┃ ┃       ┃ ┃ ┗ impl
┃ ┃       ┃ ┣ scheduler
┃ ┃       ┃ ┗ service
┃ ┃       ┣ externalapi
┃ ┃       ┃ ┣ client
┃ ┃       ┃ ┃ ┗ model
┃ ┃       ┃ ┣ config
┃ ┃       ┃ ┣ dto
┃ ┃       ┃ ┃ ┣ request
┃ ┃       ┃ ┃ ┗ response
┃ ┃       ┃ ┗ service
┃ ┃       ┣ global
┃ ┃       ┃ ┣ config
┃ ┃       ┃ ┣ entity
┃ ┃       ┃ ┗ exception
┃ ┃       ┃   ┣ autosyncconfig
┃ ┃       ┃   ┣ indexdata
┃ ┃       ┃   ┣ indexinfo
┃ ┃       ┃   ┗ request
┃ ┃       ┣ indexdata
┃ ┃       ┃ ┣ controller
┃ ┃       ┃ ┃ ┗ swagger
┃ ┃       ┃ ┣ dto
┃ ┃       ┃ ┃ ┣ command
┃ ┃       ┃ ┃ ┣ condition
┃ ┃       ┃ ┃ ┣ request
┃ ┃       ┃ ┃ ┗ response
┃ ┃       ┃ ┣ entity
┃ ┃       ┃ ┣ mapper
┃ ┃       ┃ ┣ repository
┃ ┃       ┃ ┃ ┗ impl
┃ ┃       ┃ ┗ service
┃ ┃       ┃   ┗ impl
┃ ┃       ┣ indexinfo
┃ ┃       ┃ ┣ controller
┃ ┃       ┃ ┃ ┗ swagger
┃ ┃       ┃ ┣ dto
┃ ┃       ┃ ┃ ┣ command
┃ ┃       ┃ ┃ ┣ request
┃ ┃       ┃ ┃ ┗ response
┃ ┃       ┃ ┣ entity
┃ ┃       ┃ ┣ mapper
┃ ┃       ┃ ┣ repository
┃ ┃       ┃ ┃ ┗ impl
┃ ┃       ┃ ┗ service
┃ ┃       ┃   ┗ impl
┃ ┃       ┣ sync
┃ ┃       ┃ ┣ controller
┃ ┃       ┃ ┃ ┗ swagger
┃ ┃       ┃ ┣ dto
┃ ┃       ┃ ┃ ┣ command
┃ ┃       ┃ ┃ ┣ request
┃ ┃       ┃ ┃ ┗ response
┃ ┃       ┃ ┣ entity
┃ ┃       ┃ ┣ mapper
┃ ┃       ┃ ┣ repository
┃ ┃       ┃ ┃ ┗ impl
┃ ┃       ┃ ┗ service
┃ ┃       ┃   ┗ impl
┃ ┃       ┗ FindexApplication.java
┃ ┗ resources
┃   ┣ db
┃   ┃ ┗ migration
┃   ┣ static
┃   ┣ application.yaml
┃   ┣ application-prod.yml
┃   ┣ application-local.yaml.txt
┃   ┗ application-local-h2.yml.txt
┣ test
┃ ┗ java
┃   ┗ com
┃     ┗ sb13
┃       ┗ findex
┃         ┣ global
┃         ┣ indexdata
┃         ┣ indexinfo
┃         ┣ sync
┃         ┗ FindexApplicationTests.java
┣ build.gradle
┣ settings.gradle
┣ gradlew
┣ gradlew.bat
┗ README.md
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
