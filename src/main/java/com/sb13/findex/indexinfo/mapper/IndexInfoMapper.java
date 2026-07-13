package com.sb13.findex.indexinfo.mapper;

import com.sb13.findex.indexinfo.dto.response.*;
import com.sb13.findex.indexinfo.entity.*;

import java.util.*;

// IndexInfo Entity를 IndexInfoResponse DTO로 전환
public class IndexInfoMapper {

    public static IndexInfoResponse toResponse(IndexInfo indexInfo) {

        return new IndexInfoResponse(
                indexInfo.getId(),
                indexInfo.getIndexClassification(),
                indexInfo.getIndexName(),
                indexInfo.getEmployedItemsCount(),
                indexInfo.getBasePointInTime(),
                indexInfo.getBaseIndex(),
                indexInfo.getSourceType(),
                indexInfo.isFavorite()
        );
    }

    public static List<IndexInfoResponse> toResponseList(List<IndexInfo> indexInfoList) {
        return indexInfoList.stream()
                .map(IndexInfoMapper::toResponse)
                .toList();
    }

}