package com.sb13.findex.indexinfo.service;

import com.sb13.findex.indexinfo.dto.*;
import com.sb13.findex.indexinfo.entity.*;

import java.util.*;

public interface IndexInfoService {

    // 지수 정보 목록 조회
    CursorPageResponse<IndexInfoResponse> search(
            IndexInfoSearchRequest request
    );

    // 지수 정보 등록
    IndexInfoResponse create(
            IndexInfoCreateRequest request
    );

    // 지수 정보 상세 조회
    IndexInfoResponse findById(
            Long id
    );

    // 지수 정보 수정
    IndexInfoResponse update(
            Long id,
            IndexInfoUpdateRequest request
    );

    // 지수 정보 삭제
    void delete(
            Long id
    );

    // 지수 정보 요약 목록 조회
    List<IndexInfoSummaryResponse> findSummaries();

}
