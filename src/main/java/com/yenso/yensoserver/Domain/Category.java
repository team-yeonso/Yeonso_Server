package com.yenso.yensoserver.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class Category implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "info_id")
    private Info info_id;

    @Column
    private String category;

    @Column
    private String job;
}
