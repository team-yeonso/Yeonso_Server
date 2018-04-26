package com.yenso.yensoserver.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class Jwt {

    @Value("${key}")
    private String key;

    public String builder(String value,Long exp){
        Map<String,Object> m = new HashMap<>(); // JWT PAYLOAD 원하는 내용 Input
        m.put("id",value);
        return Jwts.builder().setSubject(value).setExpiration(new Date(exp)).setIssuedAt(new Date(System.currentTimeMillis())).addClaims(m).signWith(SignatureAlgorithm.HS512,key).compact();
    }


    public Object parser(String token){
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().get("id");
    }


}
