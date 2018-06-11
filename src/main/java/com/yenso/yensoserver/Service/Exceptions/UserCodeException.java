package com.yenso.yensoserver.Service.Exceptions;

public class UserCodeException extends RuntimeException {
    public UserCodeException(String exception){
        super(exception);
    }
}
