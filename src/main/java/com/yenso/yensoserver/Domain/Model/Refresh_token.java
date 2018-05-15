package com.yenso.yensoserver.Domain.Model;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Refresh_token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String refreshToken;

    @Column
    private String token_owner;

    @Column
    private String owner_pw;

    @Builder
    public Refresh_token(String token_owner, String owner_pw) {
        this.token_owner = token_owner;
        this.owner_pw = owner_pw;
    }
}
