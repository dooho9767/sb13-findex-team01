package com.sb13.findex.indexinfo.utli;

public class IndexInfoPaginationUtils {

    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_PAGE_SIZE = 100;

    private IndexInfoPaginationUtils() {
    }

    /*
      size가 없거나 0 이하면 기본 크기 10을 사용합니다.
     */
    public static int resolveSize(Integer size) {
        if (size == null || size <= 0) {
            return DEFAULT_PAGE_SIZE;
        }

        return size;
    }
}
