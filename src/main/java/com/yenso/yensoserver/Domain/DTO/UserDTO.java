package com.yenso.yensoserver.Domain.DTO;

import com.yenso.yensoserver.Domain.Model.User;
import com.yenso.yensoserver.Domain.Model.TempUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;


@Getter
@Setter
@Component
public class UserDTO {

    private Long user_id;

    private String email;

    private String password;

    private Date timestamp;

    public UserDTO SetUserData(User user){
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.user_id = user.getUser_id();
        this.timestamp = user.getTimestamp();
        return this;
    }

    public User toEntity(){
        return User.builder()
                .email(this.email)
                .password(this.password)
                .build();
    }

    public User toEntity(User user){
        return User.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public User toEntity(TempUser userAuth){
        return User.builder()
                .email(userAuth.getEmail())
                .password(userAuth.getPassword())
                .build();
    }

}
