package com.sb13.findex.indexinfo.dto.request;

import com.sb13.findex.indexinfo.dto.command.*;
import jakarta.validation.constraints.*;

import java.math.*;
import java.time.*;

public record IndexInfoCreateRequest(
        @NotBlank(message = "지수 분류명은 필수입니다.")
        String indexClassification,

        @NotBlank(message = "지수명은 필수입니다.")
        String indexName,

        @Positive(message = "편입 종목 수는 0보다 커야 합니다.")
        int employedItemsCount,

        @NotNull(message = "기준 시점은 필수입니다.")
        LocalDate basePointInTime,

        @NotNull(message = "기준 지수는 필수입니다.")
        @Positive(message = "기준 지수는 0보다 커야 합니다.")
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
