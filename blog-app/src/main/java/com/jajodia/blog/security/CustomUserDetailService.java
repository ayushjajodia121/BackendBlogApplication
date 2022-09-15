package com.jajodia.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jajodia.blog.exception.CategoryNotFoundException;
import com.jajodia.blog.exception.ResourceNotFoundException;
import com.jajodia.blog.model.User;
import com.jajodia.blog.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	
	@Autowired
	private UserRepository userRepo;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		//********************************** loading user from database by username **********************************//
		User user = this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User ","Email: "+username,0));
		return user;
	}

}
