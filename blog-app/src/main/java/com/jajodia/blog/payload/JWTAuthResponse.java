package com.jajodia.blog.payload;


import java.util.Set;

import com.jajodia.blog.model.Role;

public class JWTAuthResponse {
	
	
	private String token;
	
	private String userName;
	
	private int userId;
	
	private Set<Role> roles;
	

	public JWTAuthResponse() {
		super();
	}
	
	
	public JWTAuthResponse(String token, String userName, int userId, Set<Role> roles) {
		super();
		this.token = token;
		this.userName = userName;
		this.userId = userId;
		this.roles = roles;
	}



	@Override
	public String toString() {
		return "JWTAuthResponse [token=" + token + ", userName=" + userName + ", userId=" + userId + ", roles=" + roles
				+ "]";
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> rolesAssigned) {
		this.roles = rolesAssigned;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
