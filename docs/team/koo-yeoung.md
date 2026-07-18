# 정구영 구현 기능 상세

(자신이 개발한 기능에 대한 사진이나 gif 파일 첨부)

## 외부 API 연동

공공데이터포털 금융위원회 지수 API 연동 기능 구현

공공데이터 API 요청/응답 DTO 구성 및 `RestClient` 기반 API 호출 서비스 개발

API 응답의 `null`, 빈 문자열, 단일 객체/배열 응답 형태를 방어적으로 처리하는 변환 로직 구현

API HTTP 응답이 성공이어도 본문 결과 코드에 업무 오류가 포함될 수 있어, Open API 오류 코드를 별도로 매핑하고 검증하도록 구현

## 수동 동기화 API

지수 정보와 지수 데이터를 수동으로 동기화하는 API 흐름 구현

외부 API 호출, 응답 데이터 검증, 도메인 데이터 저장, 동기화 결과 반환 흐름을 `SyncJobManager` 중심으로 구성

외부 API 호출이 데이터베이스 트랜잭션을 오래 점유하지 않도록 호출 계층과 저장 계층 분리

## 동기화 이력 저장 및 성능 개선

동기화 작업별 성공/실패 이력을 `SyncJob`으로 저장하는 기능 구현

`syncExecutionId`를 사용하여 한 번의 동기화 실행에서 생성된 작업 이력을 하나의 실행 단위로 관리

`CompletableFuture`와 외부 API 전용 Executor를 사용한 비동기 병렬 호출 구조 구현

대량 동기화 이력 저장을 위해 `JdbcTemplate` 기반 벌크 저장 로직 구현

외부 API 연동 코드를 `externalapi` 패키지로 분리하고, `sync` 패키지는 동기화 유스케이스와 이력 관리 중심으로 정리

## 주요 패키지/파일

- `externalapi`
- `sync.service.SyncJobManager`
- `sync.repository.SyncJobBulkRepository`
- `sync.repository.impl.SyncJobBulkRepositoryImpl`
- `sync.entity.SyncJob`
- `docs/railway-deployment.md`
- `README.md`
