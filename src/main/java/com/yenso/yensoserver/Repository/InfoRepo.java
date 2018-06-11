package com.yenso.yensoserver.Repository;

import com.yenso.yensoserver.Domain.Model.Info;
import com.yenso.yensoserver.Domain.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("infoRepo")
public interface InfoRepo extends JpaRepository<Info,Long> {

    Optional<Info> findByUserid(User userid);
}
