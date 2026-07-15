package com.sb13.findex.sync.dto.command;

import java.time.LocalDate;

public record IndexDataSyncJobTarget(
        Long indexInfoId,
        LocalDate baseDate,
        String indexClassification,
        String indexName
) {
}
