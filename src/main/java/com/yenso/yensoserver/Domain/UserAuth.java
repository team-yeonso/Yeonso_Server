package com.yenso.yensoserver.Domain;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class UserAuth {

    @Id
    private String email;

    @Column
    private String password;

    @Column(unique = true)
    private String code;

    @Builder
    public UserAuth(String email, String password, String code) {
        this.email = email;
        this.password = password;
        this.code = code;
    }
}
