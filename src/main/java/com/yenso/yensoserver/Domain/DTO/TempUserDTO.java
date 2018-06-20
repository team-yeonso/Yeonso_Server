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
    private String name;

    public TempUser toEntity(String enPassword, String code) {
        return TempUser.builder()
                .email(email)
                .password(enPassword)
                .code(code)
                .name(name)
                .build();
    }

}
