package com.yenso.yensoserver.Repository;


import com.yenso.yensoserver.Domain.DTO.UserDTO;
import com.yenso.yensoserver.Domain.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "userRepo")
public interface UserRepo extends JpaRepository<User,Long> {
    Optional<String> findByEmailAndPassword(String email,String password);

    Optional<UserDTO> findByEmail(String email);

    boolean findByEmailAndEmailIsNotNull(String email);
}
