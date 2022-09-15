package com.jajodia.blog.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jajodia.blog.config.AppConstants;
import com.jajodia.blog.exception.PasswordMismatchException;
import com.jajodia.blog.exception.ResourceNotFoundException;
import com.jajodia.blog.model.Role;
import com.jajodia.blog.model.User;
import com.jajodia.blog.payload.UserDto;
import com.jajodia.blog.repository.RoleRepo;
import com.jajodia.blog.repository.UserRepository;
import com.jajodia.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	

	//************************CREATE USER ONLY WHEN BOTH PASSWORD AND CONFPASSWORD FIELD MATCHES****************//
	@Override
	public UserDto createUser(UserDto userDto) {
		
		if(userDto.getPassword().equals(userDto.getConfPassword()))
		{
			String encodedPassword = this.passwordEncoder.encode(userDto.getPassword());
			userDto.setPassword(encodedPassword);
//			userDto.setConfPassword(encodedPassword);
			
			User user = this.dtoToUser(userDto);
			Role roleNormal = this.roleRepo.findById(AppConstants.ROLE_NORMAL).get();
			user.getRoles().add(roleNormal);
			User savedUser= this.userRepository.save(user);
			
			
			return this.userToDto(savedUser);
		}
		else 
		{
			throw new PasswordMismatchException("Please use same password in confirm password field");
		}
	}

	//********************************************** UPDATE USER BY ID *********************************************// 
	@Override
	public UserDto updateUSer(UserDto userDto, int id) {
		User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User"," id ", id));
		if(userDto.getPassword().equals(userDto.getConfPassword()))
		{
			String encodedPassword = this.passwordEncoder.encode(userDto.getPassword());
			userDto.setPassword(encodedPassword);
			
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

	//****************************************** GET USER BY ID ********************************************************//
	@Override
	public UserDto getUserById(int id) {
		User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User"," id ", id));
		UserDto userDto = userToDto(user);
		userDto.setPassword("*******");
		userDto.setConfPassword("*******");
		return userDto;
	}
	//****************************************** FETCH USER BY USERNAME *************************************************//
	
	public UserDto fetchUserByUsername(String userName) {
		Optional<User> user = userRepository.findByEmail(userName);
		UserDto userDto = userToDto(user.get());
		return userDto;
	}

	//******************************************** GET LIST OF ALL USERS **************************************************//
	@Override
	public List<UserDto> getAllUser() {
		List<User> users=userRepository.findAll();
		List<UserDto> userDtos = users.stream().map(user->userToDto(user)).collect(Collectors.toList());

		for(UserDto ud:userDtos)
		{
			ud.setConfPassword("***********");
			ud.setPassword("***********");
		}
		
		return userDtos;
	}

	//********************************************** DELETE USER BY ID ********************************************************//
	@Override
	public void deleteUserById(int id) {
		User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User"," id ", id));
		userRepository.delete(user);
	}
	
	//******************************************** CONVERTING USERDTO TO USER **********************************************//
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
	
	//********************************************* CONVERTING USER TO USERDTO ************************************************//
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