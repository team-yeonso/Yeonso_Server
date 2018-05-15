package com.yenso.yensoserver.Repository;

import com.yenso.yensoserver.Domain.Model.Celebrity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CelebrityRepo extends JpaRepository<Celebrity,Long> {

    @Query(value = "select * from celebrity where info_id=:info_id",nativeQuery = true)
    Celebrity findByInfoId(@Param("info_id") Long info_id);
}
