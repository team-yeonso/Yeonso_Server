package com.yenso.yensoserver.Service;

import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class Config {

    public boolean isEmpty(Collection <?> collection){
        return !collection.isEmpty();
    }

    public boolean isEmpty(Object obj){
        return obj != null;
    }

}
