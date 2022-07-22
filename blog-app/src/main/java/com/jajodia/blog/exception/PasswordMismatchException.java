package com.jajodia.blog.exception;

public class PasswordMismatchException extends RuntimeException {
	String Msg;

	public PasswordMismatchException(String msg) {
		super(msg);
		Msg = msg;
	}
	

}
