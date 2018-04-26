package com.yenso.yensoserver.Repository;

import com.yenso.yensoserver.Domain.Celebrity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CelebrityRepo extends JpaRepository<Celebrity,Long> {
}
