package com.sb13.findex.indexinfo.exception;

public class IndexInfoNotFoundException extends RuntimeException {

    public IndexInfoNotFoundException(Long id) {
        super("존재하지 않는 지수 정보입니다. ID: " + id);
    }

}
