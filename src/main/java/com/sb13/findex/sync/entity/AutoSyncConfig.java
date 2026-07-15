package com.sb13.findex.sync.entity;

import com.sb13.findex.indexinfo.entity.IndexInfo;

import com.sb13.findex.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/*
 지수별 자동 연동(Open API 배치 연동) 활성화 여부를 관리
 지수 정보가 등록될 때 비활성화 상태로 생성
 */
@Entity
@Table(
    name = "auto_sync_config",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_auto_sync_config_index_info_id",
            columnNames = {"index_info_id"}
        )
    }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutoSyncConfig extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "index_info_id", nullable = false)
    private IndexInfo indexInfo;

    @Setter
    @Column(nullable = false)
    private boolean enabled;

    // 추가
    @Column(name = "index_classification_snapshot", length = 30)
    private String indexClassificationSnapshot;

    @Column(name = "index_name_snapshot", length = 100)
    private String indexNameSnapshot;

}