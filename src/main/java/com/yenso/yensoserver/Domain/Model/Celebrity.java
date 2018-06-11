package com.yenso.yensoserver.Domain.Model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
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
    private Info infoValue;

    @Builder
    public Celebrity(int confidence, String celebrity, String img_path, Info infoValue) {
        this.confidence = confidence;
        this.celebrity = celebrity;
        this.img_path = img_path;
        this.infoValue = infoValue;
    }
}
