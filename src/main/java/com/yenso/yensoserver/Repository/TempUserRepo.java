package com.yenso.yensoserver.Repository;

import com.yenso.yensoserver.Domain.DTO.TempUserDTO;
import com.yenso.yensoserver.Domain.Model.TempUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;


@Component
public interface TempUserRepo extends JpaRepository<TempUser,Long> {

    boolean findByEmailAndEmailIsNotNull (String email);

    Optional<TempUserDTO> findByCode(String code);

//    @Transactional
//    @Modifying
//    @Query(value = "delete from user_auth where code=:code",nativeQuery = true)
    void deleteByCode(String code);
}
