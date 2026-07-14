package com.sb13.findex.sync.repository.impl;

import com.sb13.findex.sync.dto.command.IndexDataKey;
import com.sb13.findex.sync.dto.command.IndexInfoKey;
import com.sb13.findex.sync.repository.SyncJobBulkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SyncJobBulkRepositoryImpl implements SyncJobBulkRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public int saveInfoAll(String worker, List<IndexInfoKey> indexInfoKeys) {

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
                COALESCE(info.updated_at, now()),
                CASE
                    WHEN info.id IS NULL THEN 'FAILED'
                    ELSE 'SUCCESS'
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

    @Override
    public int saveDataAll(String worker, List<IndexDataKey> indexDataKeys) {

        String sql = """
                INSERT INTO sync_job (
                                      index_info_id,
                                      job_type,
                                      worker,
                                      job_time,
                                      result,
                                      target_date,
                                      created_at,
                                      updated_at
                                      )
                SELECT
                    req.index_info_id,
                    'INDEX_DATA',
                    ?,
                   COALESCE(data.updated_at, now()),
                    CASE
                        WHEN data.id IS NULL THEN 'FAILED'
                        ELSE 'SUCCESS'
                    END,
                    req.base_date,
                    now(),
                    now()
                FROM(
                    VALUES (?, ?)
                ) AS req(index_info_id, base_date)
                LEFT JOIN index_data data ON data.index_info_id = req.index_info_id
                AND data.base_date = req.base_date
                """;

        int[][] result = jdbcTemplate.batchUpdate(
                sql,
                indexDataKeys,
                1000, (ps, key) -> {
                    ps.setString(1, worker);
                    ps.setLong(2, key.indexInfoId());
                    ps.setDate(3, Date.valueOf(key.baseDate()));
                });

        return Arrays.stream(result)
                .flatMapToInt(Arrays::stream)
                .filter(count -> count > 0)
                .sum();
    }
}
