package com.jajodia.blog.payload;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

public class PostDto {
	
	@NotEmpty
	private String title;
	
	@NotEmpty
	private String content;

	private Date addedDate;
	
	private String imageName;
	
	private UserDto user;
	
	private CategoryDto category;
	
	public PostDto() {
		super();
	}

	public PostDto(@NotEmpty String title, @NotEmpty String content, Date addedDate, String imageName, UserDto user,
			CategoryDto category) {
		super();
		this.title = title;
		this.content = content;
		this.addedDate = addedDate;
		this.imageName = imageName;
		this.user = user;
		this.category = category;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public CategoryDto getCategory() {
		return category;
	}

	public void setCategory(CategoryDto category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	

}
