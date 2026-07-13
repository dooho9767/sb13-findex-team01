package com.sb13.findex.indexinfo.dto.response;

import java.util.*;

public record CursorPageResponse<T>(
        List<T> content,
        String nextCursor,
        Long nextIdAfter,
        int size,
        long totalElements,
        boolean hasNext
) {
}
