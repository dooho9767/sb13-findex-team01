package com.sb13.findex.indexinfo.exception;

public class DuplicateIndexInfoException extends RuntimeException {

    public DuplicateIndexInfoException() {
        super("이미 존재하는 지수 정보입니다.");
    }
}
