# 강다연 구현 기능 상세

(자신이 개발한 기능에 대한 사진이나 gif 파일 첨부)

## 대시보드 API

관심 지수 성과 조회 API 구현

지수별 차트 데이터 조회 API 구현

기간별 지수 성과 순위 조회 API 구현

## 지수 데이터 조회 API

지수 데이터 목록 조회 기능 구현

검색 조건과 정렬 조건을 반영한 커서 기반 페이지네이션 조회 흐름 구성

차트와 성과 분석 화면에서 사용할 수 있는 응답 DTO 구성

## 테스트 및 로컬 환경 설정

대시보드 관련 서비스 및 조회 흐름 테스트 보완

빠른 로컬 확인과 테스트 실행을 위한 H2 설정 추가

예외 처리와 API 응답 안정성 관련 코드 리뷰 피드백 반영

## 주요 패키지/파일

- `indexdata.controller.IndexDataController`
- `indexdata.service.DashboardIndexDataService`
- `indexdata.repository.IndexDataRepository`
- `indexdata.dto.response.IndexPerformanceResponse`
- `indexdata.dto.response.IndexChartResponse`
- `indexdata.dto.response.RankedIndexPerformanceResponse`
- `src/test/java/com/sb13/findex/indexdata`
- `src/main/resources/application-local-h2.yml.txt`
