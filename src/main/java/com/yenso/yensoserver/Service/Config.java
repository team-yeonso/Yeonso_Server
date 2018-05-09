package com.yenso.yensoserver.Service;

import org.springframework.stereotype.Component;

import java.util.Collection;

public class Config {

    public static boolean isEmpty(Collection <?> collection){
        return !collection.isEmpty();
    }

    public static boolean isEmpty(Object obj){
        return obj != null;
    }

}
