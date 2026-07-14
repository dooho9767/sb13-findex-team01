package com.sb13.findex.indexdata.service;

import com.sb13.findex.indexdata.dto.response.IndexPerformanceResponse;
import com.sb13.findex.indexdata.entity.IndexData;
import com.sb13.findex.indexdata.entity.UnitPeriodType;
import com.sb13.findex.indexdata.repository.IndexDataRepository;
import com.sb13.findex.indexdata.dto.response.ChartDataPointResponse;
import com.sb13.findex.indexdata.dto.response.IndexChartResponse;
import com.sb13.findex.indexdata.entity.ChartPeriodType;
import com.sb13.findex.indexdata.dto.response.RankedIndexPerformanceResponse;
import java.util.Comparator;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DashboardIndexDataService {

    private final IndexDataRepository indexDataRepository;

    private Optional<IndexPerformanceResponse> calculatePerformance(
            IndexData currentData,
            UnitPeriodType periodType
    ) {
        Long indexInfoId = currentData.getIndexInfo().getId();

        LocalDate beforeDate = getBeforeDate(
                currentData.getBaseDate(),
                periodType
        );

        Optional<IndexData> beforeDataOptional =
                indexDataRepository.findNearestDataOnOrBefore(
                        indexInfoId,
                        beforeDate
                );

        if (beforeDataOptional.isEmpty()) {
            return Optional.empty();
        }

        IndexData beforeData = beforeDataOptional.get();

        BigDecimal currentPrice = currentData.getClosingPrice();
        BigDecimal beforePrice = beforeData.getClosingPrice();

        if (beforePrice.compareTo(BigDecimal.ZERO) == 0) {
            return Optional.empty();
        }

        BigDecimal versus = currentPrice.subtract(beforePrice);

        BigDecimal fluctuationRate = versus
                .divide(beforePrice, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        return Optional.of(new IndexPerformanceResponse(
                indexInfoId,
                currentData.getIndexInfo().getIndexClassification(),
                currentData.getIndexInfo().getIndexName(),
                versus,
                fluctuationRate,
                currentPrice,
                beforePrice
        ));
    }

    private LocalDate getBeforeDate(
            LocalDate currentDate,
            UnitPeriodType periodType
    ) {
        return switch (periodType) {
            case DAILY -> currentDate.minusDays(1);
            case WEEKLY -> currentDate.minusWeeks(1);
            case MONTHLY -> currentDate.minusMonths(1);
        };
    }
    public List<IndexPerformanceResponse> getFavoritePerformance(
            UnitPeriodType periodType
    ) {
        return indexDataRepository.findLatestDataForFavoriteIndexes()
                .stream()
                .map(currentData -> calculatePerformance(currentData, periodType))
                .flatMap(Optional::stream)
                .toList();
    }
    public IndexChartResponse getIndexChart(
            Long indexInfoId,
            ChartPeriodType periodType
    ) {
        IndexData latestData = indexDataRepository.findNearestDataOnOrBefore(
                indexInfoId,
                LocalDate.now()
        ).orElseThrow(() -> new IllegalArgumentException(
                "지수 데이터가 존재하지 않습니다. indexInfoId=" + indexInfoId
        ));

        LocalDate endDate = latestData.getBaseDate();
        LocalDate startDate = getChartStartDate(endDate, periodType);

        LocalDate movingAverageStartDate = startDate.minusDays(40);

        List<IndexData> dataList =
                indexDataRepository.findDataByIndexInfoIdAndBaseDateBetween(
                        indexInfoId,
                        movingAverageStartDate,
                        endDate
                );

        List<ChartDataPointResponse> dataPoints = toChartDataPoints(
                dataList,
                startDate
        );

        List<ChartDataPointResponse> ma5DataPoints = toMovingAverageDataPoints(
                dataList,
                startDate,
                5
        );

        List<ChartDataPointResponse> ma20DataPoints = toMovingAverageDataPoints(
                dataList,
                startDate,
                20
        );

        return new IndexChartResponse(
                indexInfoId,
                latestData.getIndexInfo().getIndexClassification(),
                latestData.getIndexInfo().getIndexName(),
                periodType.name(),
                dataPoints,
                ma5DataPoints,
                ma20DataPoints
        );
    }
    //차트 시작일 계산
    private LocalDate getChartStartDate(
            LocalDate endDate,
            ChartPeriodType periodType
    ) {
        return switch (periodType) {
            case MONTHLY -> endDate.minusMonths(1);
            case QUARTERLY -> endDate.minusMonths(3);
            case YEARLY -> endDate.minusYears(1);
        };
    }

    //일반 종가 데이터
    private List<ChartDataPointResponse> toChartDataPoints(
            List<IndexData> dataList,
            LocalDate startDate
    ) {
        return dataList.stream()
                .filter(data -> !data.getBaseDate().isBefore(startDate))
                .map(data -> new ChartDataPointResponse(
                        data.getBaseDate(),
                        data.getClosingPrice()
                ))
                .toList();
    }

    //이동평균 데이터만들기
    private List<ChartDataPointResponse> toMovingAverageDataPoints(
            List<IndexData> dataList,
            LocalDate startDate,
            int period
    ) {
        List<ChartDataPointResponse> result = new ArrayList<>();

        for (int i = period - 1; i < dataList.size(); i++) {
            IndexData currentData = dataList.get(i);

            if (currentData.getBaseDate().isBefore(startDate)) {
                continue;
            }

            BigDecimal sum = BigDecimal.ZERO;

            for (int j = i - period + 1; j <= i; j++) {
                sum = sum.add(dataList.get(j).getClosingPrice());
            }

            BigDecimal average = sum.divide(
                    BigDecimal.valueOf(period),
                    4,
                    RoundingMode.HALF_UP
            );

            result.add(new ChartDataPointResponse(
                    currentData.getBaseDate(),
                    average
            ));
        }

        return result;
    }

    public List<RankedIndexPerformanceResponse> getPerformanceRank(
            Long indexInfoId,
            UnitPeriodType periodType,
            int limit
    ) {
        List<IndexPerformanceResponse> performances =
                indexDataRepository.findLatestDataForRanking(indexInfoId)
                        .stream()
                        .map(currentData -> calculatePerformance(currentData, periodType))
                        .flatMap(Optional::stream)
                        .sorted(
                                Comparator.comparing(
                                        IndexPerformanceResponse::fluctuationRate
                                ).reversed()
                        )
                        .limit(limit)
                        .toList();

        List<RankedIndexPerformanceResponse> result = new ArrayList<>();

        for (int i = 0; i < performances.size(); i++) {
            result.add(new RankedIndexPerformanceResponse(
                    i + 1,
                    performances.get(i)
            ));
        }

        return result;
    }
}