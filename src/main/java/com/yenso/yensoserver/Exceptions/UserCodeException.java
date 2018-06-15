package com.yenso.yensoserver.Exceptions;

public class UserCodeException extends RuntimeException {
    public UserCodeException(String exception){
        super(exception);
    }
}
