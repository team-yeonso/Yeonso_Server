package com.yenso.yensoserver.RestController;

import com.yenso.yensoserver.Repository.InfoRepo;
import com.yenso.yensoserver.Service.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping(value = "/Info")
@RestController
public class InfoController {

    @Autowired
    @Qualifier(value = "infoRepo")
    private InfoRepo infoRepo;

    @RequestMapping("/insert")
    public void insertData(@RequestBody Map<String,Object> data, @RequestHeader(value = "Authorization")String token){
        String jwt = Jwt.filterToken(token);
    }
}
