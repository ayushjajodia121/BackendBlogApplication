package com.jajodia.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jajodia.blog.payload.ApiResponse;
import com.jajodia.blog.payload.UserDto;
import com.jajodia.blog.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//create User
	@PostMapping("/createUser")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
	{
		UserDto CreatedUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(CreatedUserDto,HttpStatus.CREATED);
	}
	
	@PutMapping("/updateUser/{id}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable int id)
	{
		UserDto updatedUser = userService.updateUSer(userDto, id);
		return ResponseEntity.ok(updatedUser);
	}
	
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("id") int userId)
	{
		userService.deleteUserById(userId);
//		return new ResponseEntity(Map.of("message","User Deleted Successfully"),HttpStatus.OK);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully",true),HttpStatus.OK);
	}
	
	//fetch ALl USers
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UserDto>> fetchAllUsers()
	{
		return ResponseEntity.ok(userService.getAllUser());
	}
	
	//fetch User By ID
	@GetMapping("/getUserById/{id}")
	public ResponseEntity<UserDto> fetchUserById(@PathVariable int id)
	{
		return ResponseEntity.ok(userService.getUserById(id));
	}
	

}
