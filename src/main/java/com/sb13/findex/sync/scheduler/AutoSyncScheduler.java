package com.sb13.findex.sync.scheduler;

import com.sb13.findex.sync.dto.command.IndexDataSyncCommand;
import com.sb13.findex.sync.repository.AutoSyncConfigRepository;
import com.sb13.findex.sync.service.SyncJobManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AutoSyncScheduler {

    private static final String SCHEDULER_WORKER = "scheduler";
    // 최근 1일치 동기화
    private static final long LOOKBACK_DAYS = 1;

    private final AutoSyncConfigRepository autoSyncConfigRepository;
    private final SyncJobManager syncJobManager;

    @Scheduled(cron = "${findex.batch.auto-sync.cron}")
    public void syncEnabledIndexData() {
        List<Long> indexInfoIds = autoSyncConfigRepository.findByEnabled(true).stream()
                .map(config -> config.getIndexInfo().getId())
                .toList();

        if (indexInfoIds.isEmpty()) {
            log.info("활성화된 자동 연동 설정이 없어 배치를 종료합니다.");
            return;
        }

        LocalDate today = LocalDate.now();
        LocalDate from = today.minusDays(LOOKBACK_DAYS);

        IndexDataSyncCommand command = new IndexDataSyncCommand(indexInfoIds, from, today);
        syncJobManager.syncIndexDataList(command, SCHEDULER_WORKER);

        log.info("자동 연동 배치 실행 완료. 대상 지수 수={}, 기간={} ~ {}", indexInfoIds.size(), from, today);
    }
}