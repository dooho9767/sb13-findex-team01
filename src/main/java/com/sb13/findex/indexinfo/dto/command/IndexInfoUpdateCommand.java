package com.sb13.findex.indexinfo.dto.command;

import com.sb13.findex.indexinfo.dto.request.*;

import java.math.*;
import java.time.*;

public record IndexInfoUpdateCommand(
        Integer employedItemsCount,
        LocalDate basePointInTime,
        BigDecimal baseIndex,
        Boolean favorite
) {

    public static IndexInfoUpdateCommand from(IndexInfoUpdateRequest request) {
        return new IndexInfoUpdateCommand(
                request.employedItemsCount(),
                request.basePointInTime(),
                request.baseIndex(),
                request.favorite()
        );
    }
}
