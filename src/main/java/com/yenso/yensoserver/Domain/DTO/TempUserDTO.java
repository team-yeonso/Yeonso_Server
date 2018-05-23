package com.yenso.yensoserver.Domain.DTO;

import com.yenso.yensoserver.Domain.Model.TempUser;
import com.yenso.yensoserver.Domain.Model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class TempUserDTO {

    private String email;
    private String password;
    private String code;

    public TempUser toEntity(String enPassword, String code) {
        return TempUser.builder()
                .email(email)
                .password(enPassword)
                .code(code)
                .build();
    }

    public User converterUser(){
        return User.builder()
                .email(email)
                .password(password)
                .build();
    }

}
