package com.sb13.findex.repository.impl;

import com.sb13.findex.dto.command.IndexInfoKey;
import com.sb13.findex.repository.SyncJobBulkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SyncJobBulkRepositoryImpl implements SyncJobBulkRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public int saveAll(String worker, List<IndexInfoKey> indexInfoKeys) {

        String sql = """
                INSERT INTO sync_job (
                                      index_info_id, 
                                      job_type, 
                                      worker, 
                                      job_time, 
                                      result,
                                      created_at,
                                      updated_at
                                      ) 
                SELECT 
                info.id,
                'INDEX_INFO',
                ?,
                info.updated_at,
                CASE
                    WHEN info.id IS NULL THEN 'SUCCESS'
                    ELSE 'FAILED'
                END,
                now(),
                now()
                FROM(
                    VALUES (?, ?)
                ) AS req(index_classification, index_name)
                LEFT JOIN index_info info ON info.index_classification = req.index_classification 
                    AND info.index_name = req.index_name
                """;

        int[][] updateResults = jdbcTemplate.batchUpdate(
                sql,
                indexInfoKeys,
                1000,
                (ps, key) -> {
                    ps.setString(1, worker);
                    ps.setString(2, key.indexClassification());
                    ps.setString(3, key.indexName());
                }
        );

        return Arrays.stream(updateResults)
                .flatMapToInt(Arrays::stream)
                .filter(count -> count > 0)
                .sum();

    }
}
