package com.sb13.findex.indexdata.repository;

import com.sb13.findex.indexdata.entity.IndexData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//기본적인 저장/조회는 JpaRepository를 사용하고,
//조건 검색·정렬·커서 페이지네이션처럼 복잡한 조회는 Custom Repository로 분리
@Repository
public interface IndexDataRepository extends JpaRepository<IndexData, Long>, IndexDataRepositoryCustom{

}
