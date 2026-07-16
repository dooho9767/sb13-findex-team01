package com.sb13.findex.sync.dto.command;

import java.time.LocalDate;

public record IndexDataSyncCommand(
        Long indexInfoId,
        LocalDate baseDateFrom,
        LocalDate baseDateTo
) {
}
