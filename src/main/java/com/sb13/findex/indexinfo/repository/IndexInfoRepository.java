package com.sb13.findex.indexinfo.repository;

import com.sb13.findex.indexinfo.entity.IndexInfo;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;

@Repository
public interface IndexInfoRepository extends JpaRepository<IndexInfo, Long> {

    boolean existsByIndexClassificationAndIndexName(String indexClassification, String indexName);

}
