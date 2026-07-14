package com.sb13.findex.indexdata.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record IndexDataUpdateRequest(
    @NotNull @DecimalMin("0.0") BigDecimal marketPrice,
    @NotNull @DecimalMin("0.0") BigDecimal closingPrice,
    @NotNull @DecimalMin("0.0") BigDecimal highPrice,
    @NotNull @DecimalMin("0.0") BigDecimal lowPrice,
    @NotNull BigDecimal versus,
    @NotNull BigDecimal fluctuationRate,
    @NotNull @PositiveOrZero Long tradingQuantity,
    @NotNull @PositiveOrZero Long tradingPrice,
    @NotNull @PositiveOrZero Long marketTotalAmount
) {
}
