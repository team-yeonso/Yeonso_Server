package com.yenso.yensoserver.Domain.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Entity
public class Job implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int job_id;

    @Column(unique = true)
    private String category;

}