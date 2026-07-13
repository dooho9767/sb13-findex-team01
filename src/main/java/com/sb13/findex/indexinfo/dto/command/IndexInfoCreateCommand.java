package com.sb13.findex.indexinfo.dto.command;

import com.sb13.findex.indexinfo.dto.request.*;

import java.math.*;
import java.time.*;

public record IndexInfoCreateCommand(
        String indexClassification,
        String indexName,
        int employedItemsCount,
        LocalDate basePointInTime,
        BigDecimal baseIndex,
        boolean favorite
) {

    // Request -> Command
    public static IndexInfoCreateCommand from(
            IndexInfoCreateRequest request
    ) {
        return new IndexInfoCreateCommand(
                request.indexClassification(),
                request.indexName(),
                request.employedItemsCount(),
                request.basePointInTime(),
                request.baseIndex(),
                request.favorite()
        );
    }
}