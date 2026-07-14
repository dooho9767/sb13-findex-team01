package com.sb13.findex.sync.repository;

import com.sb13.findex.sync.dto.command.IndexDataKey;
import com.sb13.findex.sync.dto.command.IndexInfoKey;

import java.util.List;

public interface SyncJobBulkRepository {
    int saveInfoAll(String worker, List<IndexInfoKey> indexInfoKeys);

    int saveDataAll(String worker, List<IndexDataKey> indexDataKeys);
}
