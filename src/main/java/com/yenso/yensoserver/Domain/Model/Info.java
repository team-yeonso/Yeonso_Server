package com.yenso.yensoserver.Domain.Model;

import com.yenso.yensoserver.Domain.EnumEntity.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    @Column
    private String job;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job_id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User u_id;

    @Builder
    public Info(Gender gender, double weight, double height, boolean marriage, String imgPath, String job, Job job_id, User u_id) {
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.marriage = marriage;
        this.imgPath = imgPath;
        this.job = job;
        this.job_id = job_id;
        this.u_id = u_id;
    }
}