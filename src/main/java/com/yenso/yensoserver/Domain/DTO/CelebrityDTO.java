package com.yenso.yensoserver.Domain.DTO;

import com.yenso.yensoserver.Domain.Model.Celebrity;
import com.yenso.yensoserver.Domain.Model.Info;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CelebrityDTO {

    private Long id;

    private int confidence;

    private String celebrity;

    private String img_path;

    private Info infoValue;

    public Celebrity toEntity(){
        return Celebrity.builder()
                .confidence(confidence)
                .celebrity(celebrity)
                .img_path(img_path)
                .infoValue(infoValue)
                .build();
    }
}
