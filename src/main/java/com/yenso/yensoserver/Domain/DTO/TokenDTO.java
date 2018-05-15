package com.yenso.yensoserver.Domain.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDTO {
    private String refreshToken;
    private String accessToken;

}
