package com.sb13.findex.sync.entity;


import com.sb13.findex.global.entity.BaseEntity;
import com.sb13.findex.indexinfo.entity.IndexInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Table(name = "sync_job")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SyncJob extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "index_info_id")
    private IndexInfo indexInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_type", length = 30, nullable = false)
    private JobType jobType;

    @Column(name = "target_date")
    private LocalDate targetDate;

    @Column(name = "worker", length = 100, nullable = false)
    private String worker;

    @Column(name = "job_time", nullable = false)
    private LocalDateTime jobTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "result", length = 30, nullable = false)
    private JobResult result;

    @Column(name = "sync_execution_id")
    private UUID syncExecutionId;

    @Column(name = "index_name_snapshot", length = 100)
    private String indexNameSnapshot;

    @Column(name = "index_classification_snapshot", length = 30)
    private String indexClassificationSnapshot;

    public SyncJob(IndexInfo indexInfo, JobType jobType, LocalDate targetDate, String worker, LocalDateTime jobTime, JobResult result) {
        this.indexInfo = indexInfo;
        this.jobType = jobType;
        this.targetDate = targetDate;
        this.worker = worker;
        this.jobTime = jobTime;
        this.result = result;
    }

    public Long getIndexInfoId() {
        if (indexInfo == null) {
            return null;
        }
        return indexInfo.getId();
    }
}
