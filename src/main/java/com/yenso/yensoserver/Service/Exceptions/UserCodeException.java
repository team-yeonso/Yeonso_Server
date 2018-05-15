package com.yenso.yensoserver.Service.Exceptions;

public class UserCodeException extends RuntimeException {
    public UserCodeException(Exception exception){
        super(exception);
    }
}
