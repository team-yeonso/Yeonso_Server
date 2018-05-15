package com.yenso.yensoserver.Domain.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Entity
public class Address implements Serializable{

    @Id
    @OneToOne
    @JoinColumn(name = "info_id")
    private Info info_id;

    @Column
    private String state;

    @Column
    private String state_group;

    @Column
    private String road;

}
