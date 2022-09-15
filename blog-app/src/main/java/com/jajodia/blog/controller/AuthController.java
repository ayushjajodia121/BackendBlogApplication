package com.jajodia.blog.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jajodia.blog.exception.InvalidCredentialsException;
import com.jajodia.blog.model.Role;
import com.jajodia.blog.model.User;
import com.jajodia.blog.payload.JWTAuthRequest;
import com.jajodia.blog.payload.JWTAuthResponse;
import com.jajodia.blog.payload.UserDto;
import com.jajodia.blog.security.JWTTokenHelper;
import com.jajodia.blog.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private JWTTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponse> createToken(@RequestBody JWTAuthRequest request) throws Exception
	{
		
		try 
		{
			this.authenticate(request.getUsername(),request.getPassword());
		}
		catch(DisabledException e) {
			throw new Exception("USER_DISABLED",e);
		}
		catch(BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS",e);
		}
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		UserDto loggedUser = userService.fetchUserByUsername(request.getUsername());
		int userId = loggedUser.getId();
		String userFullName = loggedUser.getName();
		Set<Role> rolesAssigned = loggedUser.getRoles();
//		System.out.println(loggedUser);
//		System.out.println(userId);
//		System.out.println(userFullName);
//		System.out.println(rolesAssigned);
		String token = this.jwtTokenHelper.generateToken(userDetails);
		
		JWTAuthResponse response = new JWTAuthResponse();
		response.setToken(token);
		response.setRoles(rolesAssigned);
		response.setUserId(userId);
		response.setUserName(userFullName);
		return new ResponseEntity<JWTAuthResponse>(response,HttpStatus.OK);
		
		
	}


	private void authenticate(String username, String password) {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		try {
		this.authenticationManager.authenticate(authenticationToken);
		}catch(BadCredentialsException e) {
			throw new InvalidCredentialsException("Invalid USername or Password!!");
		}
		}

}
