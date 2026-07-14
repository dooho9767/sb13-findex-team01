package com.sb13.findex.sync.dto.command;

import java.time.LocalDate;

public record IndexDataKey(
        Long indexInfoId,
        LocalDate baseDate
) {
}
