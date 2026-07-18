# 박경석 구현 기능 상세

(자신이 개발한 기능에 대한 사진이나 gif 파일 첨부)

## 동기화 작업 이력 API

동기화 작업 목록 조회 API 구현

동기화 작업의 성공/실패 이력을 저장하고 조회하기 위한 `SyncJob` 도메인 구현

작업 유형, 결과, 작업 일시, 지수 정보 조건을 기반으로 한 동기화 이력 검색 기능 구현

## 검색/정렬/커서 페이지네이션

동기화 이력 목록 조회에 커서 기반 페이지네이션 적용

정렬 필드와 정렬 방향 검증 로직 구현

`jobType`, `result`를 문자열로 받아 검증한 뒤 도메인 값으로 변환하도록 개선

잘못된 검색 조건 입력 시 적절한 예외를 반환하도록 예외 처리 개선

## 동기화 이력 조회 성능 개선

동기화 이력 조회 시 발생할 수 있는 N+1 문제 개선

`job_time`, `id` 기반 인덱스를 추가하여 목록 조회 성능 보완

SyncJob API Swagger 문서화 및 응답 코드 정리

원인 예외 체이닝을 추가해 디버깅 가능성 개선

## 주요 패키지/파일

- `sync.entity.SyncJob`
- `sync.entity.JobType`
- `sync.entity.JobResult`
- `sync.controller.SyncJobController`
- `sync.controller.swagger.SyncJobApi`
- `sync.repository.SyncJobRepository`
- `sync.repository.impl.SyncJobRepositoryImpl`
- `sync.dto.request.SyncJobSearchRequest`
- `sync.dto.request.SyncJobSortField`
