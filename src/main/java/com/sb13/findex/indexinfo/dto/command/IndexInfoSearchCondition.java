package com.sb13.findex.indexinfo.dto.command;

import com.sb13.findex.indexinfo.dto.request.*;

public record IndexInfoSearchCondition(
        String indexClassification,
        String indexName,
        Boolean favorite,
        String cursor,
        Long idAfter,
        Integer size,
        String sortField,
        String sortDirection
) {

    // Request -> Condition
    public static IndexInfoSearchCondition from(
            IndexInfoSearchRequest request
    ) {
        return new IndexInfoSearchCondition(
                request.indexClassification(),
                request.indexName(),
                request.favorite(),
                request.cursor(),
                request.idAfter(),
                request.size(),
                request.sortField(),
                request.sortDirection()
        );
    }

}
