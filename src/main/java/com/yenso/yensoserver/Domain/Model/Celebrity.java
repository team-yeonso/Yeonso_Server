package com.yenso.yensoserver.Domain.Model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
public class Celebrity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private int confidence;

    @Column
    private String celebrity;

    @Column
    private String img_path;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "info_id")
    private Info info_field_id;

    @Builder
    public Celebrity(int confidence, String celebrity, String img_path, Info info_field_id) {
        this.confidence = confidence;
        this.celebrity = celebrity;
        this.img_path = img_path;
        this.info_field_id = info_field_id;
    }
}
