package com.sb13.findex.sync.repository;

import com.sb13.findex.sync.dto.command.IndexDataSyncJobTarget;
import com.sb13.findex.sync.dto.command.IndexInfoKey;

import java.util.List;
import java.util.UUID;

public interface SyncJobBulkRepository {
    int saveInfoAll(String worker, List<IndexInfoKey> indexInfoKeys, UUID uuid);

    int saveDataAll(String worker, List<IndexDataSyncJobTarget> indexDataSyncJobTargets, UUID uuid);
}
