package com.yenso.yensoserver.Repository;


import com.yenso.yensoserver.Domain.DTO.UserDTO;
import com.yenso.yensoserver.Domain.Model.User;
import com.yenso.yensoserver.Exceptions.UserEmailException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "userRepo")
public interface UserRepo extends JpaRepository<User,Long> {

    @Query(nativeQuery = true,value = "select * from user where user_id = :user_id")
    Optional<User> findById(@Param("user_id") Long user_id);
    Optional<User> findByEmailAndPassword(String email,String password);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    default UserDTO findEmail(String email) throws UserEmailException {
       return new UserDTO().SetUserData(this.findByEmail(email).orElseThrow(()->new UserEmailException(email + "  :  에해당하는 이메일로 정보를 찾지못함")));
    }

}
