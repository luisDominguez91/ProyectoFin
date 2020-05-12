package org.apache.fineract.portfolio.autos.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AutosRepository extends JpaRepository<Autos,Long>, JpaSpecificationExecutor<Autos>{

    public static final String FIND_AUTOS_BY_ID = "select autos from Autos autos where autos.ctmId = :ctmId";

    @Query(FIND_AUTOS_BY_ID)
    Autos getAutosById(@Param("ctmId") String ctmId);
}