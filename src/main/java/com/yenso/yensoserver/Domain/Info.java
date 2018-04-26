package com.yenso.yensoserver.Domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Info {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long info_id;

    @Enumerated(EnumType.STRING)
    @Column
    private Gender gender;

    @Column
    private double weight;

    @Column
    private double height;

    @Column
    private boolean marriage;

    @Column
    private String imgPath;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User u_id;

}