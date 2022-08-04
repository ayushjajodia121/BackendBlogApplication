package com.jajodia.blog.service;

import java.util.List;

import com.jajodia.blog.payload.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto user);
	
	UserDto updateUSer(UserDto user,int id);
	
	UserDto getUserById(int id);
	
	List<UserDto> getAllUser();
	
	void deleteUserById(int id);
	

}
