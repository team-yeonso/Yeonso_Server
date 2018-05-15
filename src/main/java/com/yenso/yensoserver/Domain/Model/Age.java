package com.yenso.yensoserver.Domain.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Entity
public class Age implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "info_id")
    private Info info_id;

    @Column
    private Integer age;

}