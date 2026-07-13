package com.sb13.findex.indexinfo.dto.response;

// 지수 정보 요약 목록 조회
public record IndexInfoSummaryResponse(
        Long id,
        String indexClassification,
        String indexName
) {
}
