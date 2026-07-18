# 유하정 구현 기능 상세

(자신이 개발한 기능에 대한 사진이나 gif 파일 첨부)

## 지수 정보 관리 API

지수 정보 등록, 조회, 수정, 삭제 API 구현

지수 정보 Entity, DTO, Mapper, Repository, Service, Controller 계층 구현

검색 조건, 정렬 조건, 커서 기반 페이지네이션을 적용한 지수 정보 목록 조회 기능 구현

지수 정보 생성/수정 요청 DTO와 서비스 계층 Command를 분리해 웹 요청 형식과 비즈니스 계층 입력 모델 구분

## 지수 정보 Open API 저장/갱신

공공데이터 API에서 수집한 지수 정보를 저장하거나 기존 정보를 갱신하는 UPSERT 흐름 구현

사용자 수정 데이터와 Open API 연동 데이터가 충돌하지 않도록 저장/갱신 정책 보완

지수 삭제 시 연결된 자동 동기화 설정을 함께 정리할 수 있는 연동 메서드 추가

## 지수 정보 예외 처리

지수 정보 도메인 예외 처리 구현

존재하지 않는 지수 정보 요청 등 잘못된 요청에 대해 적절한 API 오류 응답을 반환하도록 전역 예외 처리와 연동

트랜잭션 범위와 전파 설정을 조정해 Open API 저장/갱신 흐름의 안정성 보완

## 주요 패키지/파일

- `indexinfo.entity.IndexInfo`
- `indexinfo.controller.IndexInfoController`
- `indexinfo.service.IndexInfoService`
- `indexinfo.service.impl.IndexInfoServiceImpl`
- `indexinfo.repository.IndexInfoRepository`
- `indexinfo.repository.impl.IndexInfoRepositoryImpl`
- `indexinfo.mapper.IndexInfoMapper`
- `global.exception.indexinfo`
