package com.jajodia.blog.payload;

public class JWTAuthResponse {
	
	
	private String token;

	
	@Override
	public String toString() {
		return "JWTAuthResponse [token=" + token + "]";
	}

	public JWTAuthResponse() {
		super();
	}

	public JWTAuthResponse(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
