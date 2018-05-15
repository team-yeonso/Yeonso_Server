package com.yenso.yensoserver.Domain.Model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_id;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.DATE)
    private Date timestamp;

    @Builder
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
