package com.yenso.yensoserver.Domain.DTO;

import com.yenso.yensoserver.Domain.Model.User;
import com.yenso.yensoserver.Domain.Model.TempUser;
import org.springframework.stereotype.Component;

@Component
public class UserDTO {

    public User toEntity(TempUser userAuth){
        return User.builder()
                .email(userAuth.getEmail())
                .password(userAuth.getPassword())
                .build();
    }
}
