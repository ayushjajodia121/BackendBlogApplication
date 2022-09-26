package com.jajodia.blog.config;

public class AppConstants {
	
	public static final String PAGE_NUMBER = "0";
	public static final String PAGE_SIZE = "10";
	public static final String SORT_BY = "postId";
	public static final String SORT_DIR = "asc";
	public static final Integer ROLE_NORMAL = 501;
	public static final Integer ROLE_ADMIN = 502;
	public static final String[] PUBLIC_URLS = {
			"/api/v1/auth/login",
			"/api/v1/users/createUser",
			"/v3/api-docs/**",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"
	};
	

}
