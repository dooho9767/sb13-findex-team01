package com.sb13.findex.indexinfo.service;

import com.sb13.findex.indexinfo.entity.IndexInfo;
import com.sb13.findex.indexinfo.repository.IndexInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class IndexInfoReader {
    private final IndexInfoRepository indexInfoRepository;

    public List<IndexInfo> findIndexInfosByIds(List<Long> ids) {
        return indexInfoRepository.findAllById(ids);
    }
}
