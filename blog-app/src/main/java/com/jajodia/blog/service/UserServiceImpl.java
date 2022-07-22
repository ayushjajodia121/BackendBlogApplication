package com.jajodia.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jajodia.blog.exception.PasswordMismatchException;
import com.jajodia.blog.exception.ResourceNotFoundException;
import com.jajodia.blog.model.User;
import com.jajodia.blog.payload.UserDto;
import com.jajodia.blog.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDto createUser(UserDto userDto) {
		if(userDto.getPassword().equals(userDto.getConfPassword()))
		{

		User user = this.dtoToUser(userDto);
		User savedUser= this.userRepository.save(user);
		return this.userToDto(savedUser);
		}
		else {
			throw new PasswordMismatchException("Please use same password in confirm password field");
		}
	}

	@Override
	public UserDto updateUSer(UserDto userDto, int id) {
		User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User"," id ", id));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		user.setConfPassword(userDto.getConfPassword());
		User updatedUser = userRepository.save(user); 
		UserDto userDto1 = userToDto(updatedUser);
		return userDto1 ;
				
	}

	@Override
	public UserDto getUserById(int id) {
		User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User"," id ", id));
		UserDto userDto = userToDto(user);
		return userDto;
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> users=userRepository.findAll();
		List<UserDto> userDtos = users.stream().map(user->userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUserById(int id) {
		User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User"," id ", id));
		userRepository.delete(user);
	}
	
	public User dtoToUser(UserDto userDto)
	{
		User user = new User();
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());		
		user.setConfPassword(userDto.getConfPassword());		
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		return user;
	}
	public UserDto userToDto(User user)
	{
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setPassword(user.getPassword());		
		userDto.setConfPassword(user.getConfPassword());		
		userDto.setEmail(user.getEmail());
		userDto.setAbout(user.getAbout());
		return userDto;
	}

}
