package com.sb13.findex.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "auto_sync_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutoSyncConfig {

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

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}