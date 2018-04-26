package com.yenso.yensoserver.Domain.DTO;

import com.yenso.yensoserver.Domain.User;
import com.yenso.yensoserver.Domain.UserAuth;
import org.springframework.stereotype.Component;

@Component
public class UserDTO {

    public User toEntity(UserAuth userAuth){
        return User.builder()
                .email(userAuth.getEmail())
                .password(userAuth.getPassword())
                .build();
    }
}
