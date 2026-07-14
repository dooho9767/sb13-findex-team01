package com.sb13.findex.indexdata.dto.command;

import com.sb13.findex.indexdata.dto.request.IndexDataUpdateRequest;
import java.math.BigDecimal;

public record IndexDataUpdateCommand(
    BigDecimal marketPrice,
    BigDecimal closingPrice,
    BigDecimal highPrice,
    BigDecimal lowPrice,
    BigDecimal versus,
    BigDecimal fluctuationRate,
    Long tradingQuantity,
    Long tradingPrice,
    Long marketTotalAmount
) {
  public static IndexDataUpdateCommand from(IndexDataUpdateRequest request) {
    return new IndexDataUpdateCommand(
        request.marketPrice(),
        request.closingPrice(),
        request.highPrice(),
        request.lowPrice(),
        request.versus(),
        request.fluctuationRate(),
        request.tradingQuantity(),
        request.tradingPrice(),
        request.marketTotalAmount()
    );
  }
}
