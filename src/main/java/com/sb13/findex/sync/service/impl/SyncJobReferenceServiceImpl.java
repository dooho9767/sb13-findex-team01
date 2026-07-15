package com.sb13.findex.sync.service.impl;

import com.sb13.findex.sync.repository.SyncJobRepository;
import com.sb13.findex.sync.service.SyncJobReferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SyncJobReferenceServiceImpl implements SyncJobReferenceService {
    private final SyncJobRepository syncJobRepository;

    @Transactional
    @Override
    public int detachIndexInfo(Long indexInfoId) {
        if (indexInfoId == null) {
            return 0;
        }

        return syncJobRepository.clearIndexInfoReferenceByIndexInfoId(indexInfoId);
    }
}
