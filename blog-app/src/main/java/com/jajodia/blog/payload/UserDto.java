package com.jajodia.blog.payload;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.jajodia.blog.model.Role;

public class UserDto {
	
	private int id;
	
	@NotBlank
	@Size(min=4,message="enter name more than 4 chrachters")
//	@Min(value=4,message="enter more than 4 char")
//    @Max(value = 6 , message = "Value should be less then then equal to 6")
	private String name;
	
	@Email(message="Enter email in proper format")
	private String email;
	
	@Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",message="please enter Minimum eight characters, at least one letter and one number")
	
	private String password;
	
	@NotEmpty(message="this field should match password field")
	private String confPassword;
	
//	@Min(10)
	@Size(min=4,message="enter name more than 4 chrachters")
	private String about;
	
	private Set<Role> roles = new HashSet<>();

	
	public UserDto() {
		super();
	}
	
	public UserDto(@NotBlank @Size(min = 4, message = "enter name more than 4 chrachters") String name,
			@Email(message = "Enter email in proper format") String email,
			@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "please enter Minimum eight characters, at least one letter and one number") String password,
			@NotEmpty(message = "this field should match password field") String confPassword,
			@Size(min = 4, message = "enter name more than 4 chrachters") String about, Set<Role> roles) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.confPassword = confPassword;
		this.about = about;
		this.roles = roles;
	}




	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
//	@JsonIgnore
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getConfPassword() {
		return confPassword;
	}
	public void setConfPassword(String confPassword) {
		this.confPassword = confPassword;
	}
	
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	

}
