package com.jajodia.blog.exception;

public class PasswordMismatchException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4990489063427767735L;
	String Msg;

	public PasswordMismatchException(String msg) {
		super(msg);
		Msg = msg;
	}
	

}
