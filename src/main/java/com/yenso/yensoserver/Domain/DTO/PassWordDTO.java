package com.yenso.yensoserver.Domain.DTO;

import com.yenso.yensoserver.Service.Response.FindPassword;

public class PassWordDTO {

    private String email;

    public FindPassword toEntity(){
        return new FindPassword(email);
    }
}
