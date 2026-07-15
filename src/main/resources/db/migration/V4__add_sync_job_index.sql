-- SyncJob 목록 조회 성능 개선을 위한 인덱스 추가
-- job_time, id 기준 정렬 시 Seq Scan + Sort 발생 문제 해결
-- (개선 전: 44.470ms → 개선 후: 0.671ms, 10만 건 기준)
CREATE INDEX idx_sync_job_job_time_id ON sync_job (job_time DESC, id DESC);