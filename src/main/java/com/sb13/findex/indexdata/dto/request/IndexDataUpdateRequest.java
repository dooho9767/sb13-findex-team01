package com.sb13.findex.indexdata.dto.request;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record IndexDataUpdateRequest(
    @NotNull BigDecimal marketPrice,
    @NotNull BigDecimal closingPrice,
    @NotNull BigDecimal highPrice,
    @NotNull BigDecimal lowPrice,
    @NotNull BigDecimal versus,
    @NotNull BigDecimal fluctuationRate,
    @NotNull Long tradingQuantity,
    @NotNull Long tradingPrice,
    @NotNull Long marketTotalAmount
) {
}
