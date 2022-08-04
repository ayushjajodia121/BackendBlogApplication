package com.jajodia.blog.payload;

import javax.validation.constraints.NotEmpty;

public class CategoryDto {

	private int id;
	
	@NotEmpty
	private String categoryTitle;
	
	@NotEmpty
	private String categoryDescription;
	
	
	public CategoryDto() {
		super();
	}
	public CategoryDto(String categoryTitle, String categoryDescription) {
		super();
		this.categoryTitle = categoryTitle;
		this.categoryDescription = categoryDescription;
	}
	public String getCategoryTitle() {
		return categoryTitle;
	}
	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}
	public String getCategoryDescription() {
		return categoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
