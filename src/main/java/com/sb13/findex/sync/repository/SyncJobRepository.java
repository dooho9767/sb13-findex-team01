package com.sb13.findex.sync.repository;

import com.sb13.findex.sync.entity.SyncJob;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SyncJobRepository extends JpaRepository<SyncJob, Long>, SyncJobRepositoryCustom, SyncJobBulkRepository {

    @EntityGraph(attributePaths = {"indexInfo"})
    List<SyncJob> findBySyncExecutionId(UUID syncExecutionId);
}
