package com.sb13.findex.indexinfo.dto.request;

import com.sb13.findex.indexinfo.dto.command.*;
import jakarta.validation.constraints.*;

public record IndexInfoSearchRequest(
        String indexClassification,
        String indexName,
        Boolean favorite,
        String cursor,
        @Positive
        Long idAfter,
        @Positive
        Integer size,
        String sortField,
        String sortDirection

) {
    public IndexInfoSearchCondition toCondition() {
        return new IndexInfoSearchCondition(
                indexClassification,
                indexName,
                favorite,
                cursor,
                idAfter,
                size,
                sortField,
                sortDirection
        );

    }
}
