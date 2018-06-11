package com.yenso.yensoserver.Repository;

import com.yenso.yensoserver.Domain.Model.Celebrity;
import com.yenso.yensoserver.Domain.Model.Info;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CelebrityRepo extends JpaRepository<Celebrity,Long> {

    Optional<Celebrity> findByInfoValue(Info infoValue);
}
