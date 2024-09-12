package com.jajodia.blog.exception;

public class UserTokenLoggedOutException extends RuntimeException {
	/**
	 *
	 */
	private static final long serialVersionUID = -4990489063427767735L;
	String Msg;

	public UserTokenLoggedOutException(String msg) {
		super(msg);
		Msg = msg;
	}
	

}
