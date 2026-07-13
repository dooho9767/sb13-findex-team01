package com.sb13.findex.sync.dto.request;

public enum SyncJobSortField {
    TARGET_DATE,
    JOB_TIME;


    public static SyncJobSortField from(String value){
        if (value == null || value.isBlank()) {
            return JOB_TIME;
            //값 자체가 없다면 기본값 처리
        }

        // 공백 제거
        String trimmed = value.trim();
        for (SyncJobSortField field : values()) {
            if (field.name().equalsIgnoreCase(trimmed)) {
                return field;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 sortField 값입니다: " + value);
    }
}
