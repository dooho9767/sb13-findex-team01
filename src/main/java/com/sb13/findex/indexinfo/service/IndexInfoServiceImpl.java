package com.sb13.findex.indexinfo.service;

import com.sb13.findex.indexinfo.dto.*;
import com.sb13.findex.indexinfo.entity.*;
import com.sb13.findex.indexinfo.mapper.*;
import com.sb13.findex.indexinfo.repository.*;
import com.sb13.findex.indexinfo.utli.*;
import com.sb13.findex.sync.entity.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IndexInfoServiceImpl implements IndexInfoService {

    private final IndexInfoRepository indexInfoRepository;

    @Override
    public CursorPageResponse<IndexInfoResponse> search(IndexInfoSearchCondition condition) {
        int size = IndexInfoPaginationUtils.resolveSize(condition.size());

        List<IndexInfo> found =
                indexInfoRepository.searchIndexInfo(condition);

        boolean hasNext = found.size() > size;

        List<IndexInfo> content = hasNext
                ? found.subList(0, size)
                : found;

        List<IndexInfoResponse> responses = IndexInfoMapper.toResponseList(content);

        long totalElements = indexInfoRepository.countIndexInfo(condition);

        String nextCursor = null;
        Long nextIdAfter = null;

        if(hasNext && !content.isEmpty()) {
            IndexInfo last = content.get(content.size() - 1);
            nextCursor = getCursorValue(last, condition.sortField());
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
    public IndexInfoResponse create(IndexInfoCreateCommand command) {

        String indexClassification = command.indexClassification().strip();
        String indexName = command.indexName().strip();

        if(indexInfoRepository.existsByIndexClassificationAndIndexName(
                indexClassification, indexName
        )) {
            throw new IllegalArgumentException(
                    "이미 존재하는 지수 정보입니다."
            );
        }
        IndexInfo indexInfo = IndexInfo.builder()
                .indexClassification(indexClassification)
                .indexName(indexName)
                .employedItemsCount(command.employedItemsCount())
                .basePointInTime(command.basePointInTime())
                .baseIndex(command.baseIndex())
                .sourceType(SourceType.USER)
                .favorite(command.favorite())
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
    public IndexInfoResponse update(Long id, IndexInfoUpdateCommand command) {
        IndexInfo indexInfo = indexInfoRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 지수 정보입니다. ID= " + id));

        indexInfo.update(
                command.employedItemsCount(),
                command.basePointInTime(),
                command.baseIndex(),
                command.favorite()
        );

        return IndexInfoMapper.toResponse(indexInfoRepository.save(indexInfo));
    }

    @Override
    @Transactional
    public void delete(Long id) {

        IndexInfo indexInfo = indexInfoRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException(
                        "존재하지 않는 지수 정보입니다. ID: " + id));

        indexInfoRepository.delete(indexInfo);
    }

    @Override
    public List<IndexInfoSummaryResponse> findSummaries() {
        return indexInfoRepository.findAllSummaries();
    }

    private String getCursorValue(IndexInfo indexInfo, String sortFieldValue) {
        IndexInfoSortField sortField = IndexInfoSortField.from(sortFieldValue);

        return switch (sortField) {
            case INDEX_CLASSIFICATION -> indexInfo.getIndexClassification();
            case INDEX_NAME -> indexInfo.getIndexName();
            case EMPLOYED_ITEMS_COUNT -> String.valueOf(indexInfo.getEmployedItemsCount());
        };
    }
}
