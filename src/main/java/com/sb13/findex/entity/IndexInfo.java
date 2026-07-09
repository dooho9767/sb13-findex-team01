package com.sb13.findex.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.*;
import java.time.*;

@Entity
@Getter
@Table(name = "index_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IndexInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "index_classification", length = 30, nullable = false)
    private String indexClassification;

    @Column(name = "index_name", length = 100, nullable = false)
    private String indexName;

    @Column(name = "employed_items_count")
    private int employedItemsCount;

    @Column(name = "base_point_in_time", nullable = false)
    private LocalDateTime basePointInTime;

    @Column(name = "base_index", nullable = false)
    private BigDecimal baseIndex;

    @Column(name = "source_type", length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private SourceType sourceType;

    @Column(name = "favorite")
    private boolean favorite;


}
