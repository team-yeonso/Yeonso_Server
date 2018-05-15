package com.yenso.yensoserver.Domain.DTO;

import com.yenso.yensoserver.Domain.Model.User;
import com.yenso.yensoserver.Domain.Model.TempUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Getter
@Setter
@Component
public class UserDTO {

    private Long user_id;

    private String email;

    private String password;

    private Timestamp timestamp;

    public User toEntity(){
        return User.builder()
                .email(this.email)
                .password(this.password)
                .build();
    }
    public User toEntity(TempUserDTO userAuth){
        return User.builder()
                .email(userAuth.getEmail())
                .password(userAuth.getPassword())
                .build();
    }
}
