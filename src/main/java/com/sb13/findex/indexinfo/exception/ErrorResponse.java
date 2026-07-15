package com.sb13.findex.indexinfo.exception;

import java.time.*;

public record ErrorResponse(
        Instant timestamp,
        int status,
        String message,
        String details
) {
}
