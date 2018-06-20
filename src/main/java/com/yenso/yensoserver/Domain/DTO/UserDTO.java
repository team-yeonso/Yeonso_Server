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

    private String name;

    private Date timestamp;

    public UserDTO SetUserData(User user){
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.user_id = user.getUser_id();
        this.timestamp = user.getTimestamp();
        this.name = user.getName();
        return this;
    }

    public User toEntity(){
        return User.builder()
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .build();
    }

    public User toEntity(TempUser userAuth){
        return User.builder()
                .email(userAuth.getEmail())
                .password(userAuth.getPassword())
                .name(userAuth.getName())
                .build();
    }

}
