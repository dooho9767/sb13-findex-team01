package com.sb13.findex.indexinfo.dto.request;

import com.sb13.findex.indexinfo.dto.command.*;
import jakarta.validation.constraints.*;

import java.math.*;
import java.time.*;

public record IndexInfoUpdateRequest(
       @Positive
        Integer employedItemsCount,
        LocalDate basePointInTime,
        @Positive
        BigDecimal baseIndex,
        Boolean favorite
) {
    public IndexInfoUpdateCommand toCommand() {
        return new IndexInfoUpdateCommand(
                employedItemsCount,
                basePointInTime,
                baseIndex,
                favorite
        );
    }
}
