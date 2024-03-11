package com.jajodia.blog.exception;

public class UserTokenLoggedOutExpiredException extends RuntimeException {
    String Msg;

    public UserTokenLoggedOutExpiredException(String msg) {
        super(msg);
        Msg = msg;
    }
}
