package com.yenso.yensoserver.Repository;

import com.yenso.yensoserver.Domain.Model.TempUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;


@Component
public interface UserAuthRepo extends JpaRepository<TempUser,Long> {

    @Query(value = "select * from user_auth where email=:email",nativeQuery = true)
    TempUser findUser(@Param("email") String email);

    @Transactional
    @Query(value = "select * from user_auth where code=:code",nativeQuery = true)
    TempUser findUserInfo(@Param("code") String code);

    @Transactional
    @Modifying
    @Query(value = "delete from user_auth where code=:code",nativeQuery = true)
    void deleteAuthUser(@Param("code") String code);
}
