package com.sb13.findex.repository;

import com.sb13.findex.dto.command.IndexInfoKey;

import java.util.List;

public interface SyncJobBulkRepository {
    int saveAll(String worker, List<IndexInfoKey> indexInfoKeys);
}
