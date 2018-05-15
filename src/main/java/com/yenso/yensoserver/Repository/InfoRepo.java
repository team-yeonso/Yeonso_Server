package com.yenso.yensoserver.Repository;

import com.yenso.yensoserver.Domain.Model.Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("infoRepo")
public interface InfoRepo extends JpaRepository<Info,Long> {

    @Query(value = "select * from info where user_id=:user_id",nativeQuery = true)
    Info findByUserId(@Param("user_id") Long user_id);
}
