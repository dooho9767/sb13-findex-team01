package com.sb13.findex.indexinfo.service;

import com.sb13.findex.indexinfo.dto.*;
import com.sb13.findex.indexinfo.entity.*;
import com.sb13.findex.indexinfo.mapper.*;
import com.sb13.findex.indexinfo.repository.*;
import com.sb13.findex.sync.entity.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IndexInfoServiceImpl implements IndexInfoService {

    private static final int DEFAULT_SIZE = 10;

    private final IndexInfoRepository indexInfoRepository;

    @Override
    public CursorPageResponse<IndexInfoResponse> search(IndexInfoSearchRequest request) {
        int size =getSize(request.size());

        List<IndexInfo> found =
                indexInfoRepository.searchIndexInfo(request);

        boolean hasNext = found.size() > size;

        List<IndexInfo> content = hasNext
                ? found.subList(0, size)
                : found;

        List<IndexInfoResponse> responses = IndexInfoMapper.toResponseList(content);

        long totalElements = indexInfoRepository.countIndexInfo(request);

        String nextCursor = null;
        Long nextIdAfter = null;

        if(hasNext && !content.isEmpty()) {
            IndexInfo last = content.get(content.size() - 1);
            nextCursor = getCursorValue(last, request.sortField());
            nextIdAfter = last.getId();
        }

        return new CursorPageResponse<>(
                responses,
                nextCursor,
                nextIdAfter,
                responses.size(),
                totalElements,
                hasNext
        );
    }

    @Override
    @Transactional
    public IndexInfoResponse create(IndexInfoCreateRequest request) {
        if(indexInfoRepository.existsByIndexClassificationAndIndexName(
                request.inedxClassification(),
                request.indexName()
        )) {
            throw new IllegalArgumentException(
                    "이미 존재하는 지수 정보입니다."
            );
        }
        IndexInfo indexInfo = IndexInfo.builder()
                .indexClassification(request.inedxClassification())
                .indexName(request.indexName())
                .employedItemsCount(request.employedItemsCount())
                .basePointInTime(request.basePointInTime())
                .baseIndex(request.baseIndex())
                .sourceType(SourceType.USER)
                .favorite(request.favorite())
                .build();

        IndexInfo savedIndexInfo = indexInfoRepository.save(indexInfo);

        return IndexInfoMapper.toResponse(savedIndexInfo);
    }

    @Override
    public IndexInfoResponse findById(Long id) {
        IndexInfo indexInfo = indexInfoRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 지수 정보 입니다. ID: " +id));

        return IndexInfoMapper.toResponse(indexInfo);
    }

    @Override
    @Transactional
    public IndexInfoResponse update(Long id, IndexInfoUpdateRequest request) {
        IndexInfo indexInfo = indexInfoRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 지수 정보입니다. ID= " + id));

        indexInfo.update(
                request.employedItemsCount(),
                request.basePointInTime(),
                request.baseIndex(),
                request.favorite()
        );

        return IndexInfoMapper.toResponse(indexInfoRepository.save(indexInfo));
    }

    @Override
    @Transactional
    public void delete(Long id) {

        IndexInfo indexInfo = indexInfoRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException(
                        "존제하지 않는 지수의 정보입니다. ID: " + id));

        indexInfoRepository.delete(indexInfo);
    }

    @Override
    public List<IndexInfoSummaryResponse> findSummaries() {
        List<IndexInfo> indexInfos = indexInfoRepository.findAll();

        return IndexInfoMapper.toSummeryResponseList(indexInfos);
    }

    private int getSize(Integer size) {
        if (size == null || size <= 0) {
            return DEFAULT_SIZE;
        }

        return size;
    }

    private String getCursorValue(IndexInfo indexInfo, String sortFieldValue) {
        IndexInfoSortField sortField = getSortField(sortFieldValue);

        return switch (sortField) {
            case INDEX_CLASSIFICATION -> indexInfo.getIndexClassification();
            case INDEX_NAME -> indexInfo.getIndexName();
            case EMPLOYED_ITEMS_COUNT -> String.valueOf(indexInfo.getEmployedItemsCount());
        };
    }

    private IndexInfoSortField getSortField(String sortField) {
        if (sortField == null || sortField.isBlank()) {
            return IndexInfoSortField.INDEX_NAME;
        }

        return IndexInfoSortField.from(sortField);
    }
}
