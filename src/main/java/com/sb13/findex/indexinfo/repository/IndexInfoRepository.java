package com.sb13.findex.indexinfo.repository;

import com.sb13.findex.indexinfo.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;

import java.math.*;
import java.time.*;
import java.util.*;


@Repository
public interface IndexInfoRepository extends IndexInfoRepositoryCustom, JpaRepository<IndexInfo, Long> {

    boolean existsByIndexClassificationAndIndexName(String indexClassification, String indexName);

    Optional<IndexInfo> findByIndexClassificationAndIndexName(
            String indexClassification,
            String indexName
    );

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(
            value = """
                INSERT INTO index_info (
                    index_classification,
                    index_name,
                    employed_items_count,
                    base_point_in_time,
                    base_index,
                    source_type,
                    favorite,
                    created_at,
                    updated_at
                )
                VALUES (
                    :indexClassification,
                    :indexName,
                    :employedItemsCount,
                    :basePointInTime,
                    :baseIndex,
                    :sourceType,                  
                    false,
                    CURRENT_TIMESTAMP,
                    CURRENT_TIMESTAMP
                )
                ON CONFLICT (
                    index_classification,
                    index_name
                )
                DO UPDATE SET
                    employed_items_count = EXCLUDED.employed_items_count,
                    base_point_in_time = EXCLUDED.base_point_in_time,
                    base_index = EXCLUDED.base_index,
                    updated_at = CURRENT_TIMESTAMP
                WHERE index_info.source_type = :sourceType
                """,
            nativeQuery = true
    )
    int upsertOpenApiIndexInfo(
            @Param("indexClassification")
            String indexClassification,

            @Param("indexName")
            String indexName,

            @Param("employedItemsCount")
            int employedItemsCount,

            @Param("basePointInTime")
            LocalDate basePointInTime,

            @Param("baseIndex")
            BigDecimal baseIndex,

            @Param("sourceType")
            String sourceType
    );
}
