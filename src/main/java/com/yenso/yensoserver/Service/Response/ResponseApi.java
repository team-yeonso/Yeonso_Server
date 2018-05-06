package com.yenso.yensoserver.Service.Response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ResponseApi {

    private Integer confidence;
    private String celebrity;
    private String celebrity_path;
    private String img_path;

}
