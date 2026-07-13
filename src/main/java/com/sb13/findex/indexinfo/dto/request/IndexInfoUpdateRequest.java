package com.sb13.findex.indexinfo.dto.request;

import java.math.*;
import java.time.*;

public record IndexInfoUpdateRequest(
        Integer employedItemsCount,
        LocalDate basePointInTime,
        BigDecimal baseIndex,
        Boolean favorite
) {
}
