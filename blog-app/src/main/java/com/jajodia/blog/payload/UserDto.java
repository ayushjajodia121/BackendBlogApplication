package com.jajodia.blog.payload;



public class UserDto {
	
	private int id;
	private String name;
	private String email;
	private String password;
	private String confPassword;
	private String about;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserDto(String name, String email, String password, String confPassword, String about) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.confPassword = confPassword;
		this.about = about;
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
	public UserDto() {
		super();
	}

}
