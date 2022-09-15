package com.jajodia.blog.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jajodia.blog.exception.PasswordMismatchException;
import com.jajodia.blog.exception.ResourceNotFoundException;
import com.jajodia.blog.model.User;
import com.jajodia.blog.payload.UserDto;
import com.jajodia.blog.repository.UserRepository;
import com.jajodia.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	//create user only when password and confirm password matches
	@Override
	public UserDto createUser(UserDto userDto) {
		
		if(userDto.getPassword().equals(userDto.getConfPassword()))
		{
			String encodedPassword = this.passwordEncoder.encode(userDto.getPassword());
			userDto.setPassword(encodedPassword);
//			userDto.setConfPassword(encodedPassword);
			User user = this.dtoToUser(userDto);
			User savedUser= this.userRepository.save(user);
			return this.userToDto(savedUser);
		}
		else 
		{
			throw new PasswordMismatchException("Please use same password in confirm password field");
		}
	}

	//update user 
	@Override
	public UserDto updateUSer(UserDto userDto, int id) {
		User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User"," id ", id));
		if(userDto.getPassword().equals(userDto.getConfPassword()))
		{
			user.setName(userDto.getName());
			user.setEmail(userDto.getEmail());
			user.setAbout(userDto.getAbout());
			user.setPassword(userDto.getPassword());
			user.setConfPassword(userDto.getConfPassword());
			User updatedUser = userRepository.save(user); 
			UserDto userDto1 = userToDto(updatedUser);
			return userDto1 ;
		}
		else 
		{
			throw new PasswordMismatchException("Please use same password in confirm password field");
		}
				
	}

	//get user by ID
	@Override
	public UserDto getUserById(int id) {
		User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User"," id ", id));
		UserDto userDto = userToDto(user);
		return userDto;
	}

	//get list of all users
	@Override
	public List<UserDto> getAllUser() {
		List<User> users=userRepository.findAll();
		List<UserDto> userDtos = users.stream().map(user->userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	//************************************ Delete user by ID ********************************************************//
	@Override
	public void deleteUserById(int id) {
		User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User"," id ", id));
		userRepository.delete(user);
	}
	
	//converting userDto entity to user entity
	public User dtoToUser(UserDto userDto)
	{
		User user = this.modelMapper.map(userDto, User.class);
		
		//		user.setId(userDto.getId());
		//		user.setName(userDto.getName());
		//		user.setPassword(userDto.getPassword());		
		//		user.setConfPassword(userDto.getConfPassword());		
		//		user.setEmail(userDto.getEmail());
		//		user.setAbout(userDto.getAbout());
		return user;
	}
	
	//converting user entity to userDto entity
	public UserDto userToDto(User user)
	{
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		//		userDto.setId(user.getId());
		//		userDto.setName(user.getName());
		//		userDto.setPassword(user.getPassword());		
		//		userDto.setConfPassword(user.getConfPassword());		
		//		userDto.setEmail(user.getEmail());
		//		userDto.setAbout(user.getAbout());
		return userDto;
	}

}
