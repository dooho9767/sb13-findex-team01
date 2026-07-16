package com.sb13.findex.indexdata.controller;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.constraints.Min;
import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

class IndexDataApiContractTest {

    @Test
    void dashboardSwaggerApiMethodsMatchControllerSignatures() throws Exception {
        assertControllerImplementsApiMethod("getFavoritePerformance", com.sb13.findex.indexdata.entity.UnitPeriodType.class);
        assertControllerImplementsApiMethod("getIndexChart", Long.class, com.sb13.findex.indexdata.entity.ChartPeriodType.class);
        assertControllerImplementsApiMethod(
                "getPerformanceRank",
                Long.class,
                com.sb13.findex.indexdata.entity.UnitPeriodType.class,
                int.class
        );
    }

    @Test
    void dashboardControllerRequestParametersMatchDocumentedContract() throws Exception {
        Method favorite = IndexDataController.class.getMethod(
                "getFavoritePerformance",
                com.sb13.findex.indexdata.entity.UnitPeriodType.class
        );
        assertThat(favorite.getAnnotation(GetMapping.class).value())
                .containsExactly("/performance/favorite");
        RequestParam favoritePeriodType = favorite.getParameters()[0].getAnnotation(RequestParam.class);
        assertThat(favoritePeriodType.defaultValue()).isEqualTo("DAILY");

        Method chart = IndexDataController.class.getMethod(
                "getIndexChart",
                Long.class,
                com.sb13.findex.indexdata.entity.ChartPeriodType.class
        );
        assertThat(chart.getAnnotation(GetMapping.class).value()).containsExactly("/{id}/chart");
        assertThat(chart.getParameters()[0].getAnnotation(PathVariable.class)).isNotNull();
        RequestParam chartPeriodType = chart.getParameters()[1].getAnnotation(RequestParam.class);
        assertThat(chartPeriodType.defaultValue()).isEqualTo("MONTHLY");

        Method rank = IndexDataController.class.getMethod(
                "getPerformanceRank",
                Long.class,
                com.sb13.findex.indexdata.entity.UnitPeriodType.class,
                int.class
        );
        assertThat(rank.getAnnotation(GetMapping.class).value()).containsExactly("/performance/rank");
        assertThat(rank.getParameters()[0].getAnnotation(RequestParam.class).required()).isFalse();
        assertThat(rank.getParameters()[1].getAnnotation(RequestParam.class).defaultValue()).isEqualTo("DAILY");
        assertThat(rank.getParameters()[2].getAnnotation(RequestParam.class).defaultValue()).isEqualTo("10");
        assertThat(rank.getParameters()[2].getAnnotation(Min.class).value()).isEqualTo(1);
    }

    private void assertControllerImplementsApiMethod(String methodName, Class<?>... parameterTypes) throws Exception {
        Method apiMethod = IndexDataApi.class.getMethod(methodName, parameterTypes);
        Method controllerMethod = IndexDataController.class.getMethod(methodName, parameterTypes);

        assertThat(controllerMethod.getReturnType()).isEqualTo(apiMethod.getReturnType());
    }
}
