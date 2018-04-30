package com.yenso.yensoserver.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@Entity
public class Age implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "info_id")
    private Info info_id;

    @Column
    private Integer age;

}