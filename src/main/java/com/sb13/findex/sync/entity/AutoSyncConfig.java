package com.sb13.findex.sync.entity;

import com.sb13.findex.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "auto_sync_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutoSyncConfig extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    26/07/09 16:40 기준
    IndexInfo 엔티티가 아직 없으니 우선 FK만 Long으로
    */
    @Column(name = "index_info_id", nullable = false)
    private Long indexInfoId;

    @Column(nullable = false)
    private boolean enabled;

}