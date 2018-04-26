package com.yenso.yensoserver.Repository;


import com.yenso.yensoserver.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends JpaRepository<User,Long> {
    @Query(value = "select user_id from user where email=:email and password=:password",nativeQuery = true)
    String findByEmail(@Param("email") String email,@Param("password") String password);

    @Query(value = "select * from user where email=:email",nativeQuery = true)
    User findUser(@Param("email") String email);
}
