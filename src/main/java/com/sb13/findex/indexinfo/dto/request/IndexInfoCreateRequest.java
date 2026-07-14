package com.sb13.findex.indexinfo.dto.request;

import com.sb13.findex.indexinfo.dto.command.*;
import jakarta.validation.constraints.*;

import java.math.*;
import java.time.*;

public record IndexInfoCreateRequest(
        @NotBlank
        String indexClassification,
        @NotBlank
        String indexName,
        @Positive
        int employedItemsCount,
        @NotNull
        LocalDate basePointInTime,
        @Positive
        @NotNull
        BigDecimal baseIndex,
        boolean favorite
) {
    public IndexInfoCreateCommand toCommand() {
        return new IndexInfoCreateCommand(
                indexClassification,
                indexName,
                employedItemsCount,
                basePointInTime,
                baseIndex,
                favorite
        );
    }
}
