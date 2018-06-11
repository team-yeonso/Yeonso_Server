package com.yenso.yensoserver.Domain.Model;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class token {

    @Id
    private String refreshToken;

    @Column
    private String token_owner;

    @Column
    private String owner_pw;

    @Builder
    public token(String token_owner, String owner_pw) {
        this.token_owner = token_owner;
        this.owner_pw = owner_pw;
    }
}
