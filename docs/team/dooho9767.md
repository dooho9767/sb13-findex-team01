# 김두호 구현 기능 상세

(자신이 개발한 기능에 대한 사진이나 gif 파일 첨부)

## 자동 동기화 설정 API

자동 동기화 설정 목록 조회 API 구현

자동 동기화 설정 변경 API 구현

지수별 자동 동기화 설정을 관리하는 `AutoSyncConfig` 도메인 구현

## 자동 동기화 스케줄러

스케줄러 기반 자동 동기화 실행 흐름 구현

자동 동기화가 활성화된 지수를 대상으로 동기화 요청을 생성하는 로직 구현

자동 동기화 대상 날짜 범위와 연동일 계산 로직 보완

## 자동 동기화 성능 및 검증

자동 동기화 설정 중복 등록 방지 검증 구현

지수 정보 생성 시 자동 동기화 설정을 자동으로 생성하거나 보장하는 `createIfAbsent` 흐름 구현

지수 삭제 시 연결된 자동 동기화 설정을 정리하는 삭제 로직 보완

자동 동기화 대상 조회 시 N+1 문제를 줄이기 위한 JOIN 기반 조회 로직 개선

자동 동기화 설정 API Swagger 문서화

## 주요 패키지/파일

- `autosyncconfig.entity.AutoSyncConfig`
- `autosyncconfig.controller.AutoSyncConfigController`
- `autosyncconfig.service.AutoSyncConfigService`
- `autosyncconfig.scheduler.AutoSyncScheduler`
- `autosyncconfig.repository.AutoSyncConfigRepository`
- `autosyncconfig.repository.impl.AutoSyncConfigRepositoryImpl`
- `global.exception.autosyncconfig`
