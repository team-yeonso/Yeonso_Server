package com.yenso.yensoserver.Domain.Model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class TempUser {

    @Id
    private String email;

    @Column
    private String password;

    @Column(unique = true)
    private String code;

    @Column
    private String name;

    @Builder
    public TempUser(String email, String password, String code, String name) {
        this.email = email;
        this.password = password;
        this.code = code;
        this.name = name;
    }
}
