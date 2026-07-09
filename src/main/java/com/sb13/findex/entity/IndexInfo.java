package com.sb13.findex.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.*;
import java.time.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IndexInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 지수 정보 ID

    private String indexClassification; // 지수 분류명

    private String indexName; // 지수명

    private int employedItemsCount; // 구성 종목 수

    private LocalDateTime basePointInTime; // 기준 시점

    private BigDecimal baseIndex; // 기준 지수

    @Enumerated(EnumType.STRING)
    private SourceType sourceType; // 데이터 출처 유형

    private boolean favorite; // 즐겨찾기 여부


}
