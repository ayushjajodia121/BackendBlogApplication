package com.jajodia.blog.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jajodia.blog.exception.ResourceNotFoundException;
import com.jajodia.blog.model.Category;
import com.jajodia.blog.payload.CategoryDto;
import com.jajodia.blog.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	
	//get All categories
	public List<CategoryDto> getAllCategory()
	{
		List<Category> categories = categoryRepository.findAll();
		List<CategoryDto> categoryDtos = categories.stream().map((category)->modelMapper.map(category,CategoryDto.class)).collect(Collectors.toList());
		return categoryDtos;	
	}
	//get single category
	public CategoryDto getCatgoryById(int id)
	{
		Category category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category"," id ", id));
		return modelMapper.map(category, CategoryDto.class);
	}
	
	//get category by name of the title
	public List<CategoryDto> getCategoryByTitle(String name)
	{
		List<Category> categories = categoryRepository.findByCategoryTitleContaining(name);
		List<CategoryDto> categoryDtos = categories.stream().map((category)->modelMapper.map(category,CategoryDto.class)).collect(Collectors.toList());
		return categoryDtos;
		
	}
	//create category
	public CategoryDto createCategory(CategoryDto categoryDto)
	{
		Category cat=modelMapper.map(categoryDto, Category.class);
		Category addedCategory = categoryRepository.save(cat);
		return modelMapper.map(addedCategory, CategoryDto.class);
	}
	//update a category
	public CategoryDto updateCategory(CategoryDto categoryDto,int id)
	{
		Category cat= modelMapper.map(categoryDto, Category.class);
		Category oldCat = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category"," id ", id));
		oldCat.setCategoryTitle(cat.getCategoryTitle());
		oldCat.setCategoryDescription(cat.getCategoryDescription());
		
		Category updatedCategory = categoryRepository.save(oldCat);
		return modelMapper.map(updatedCategory, CategoryDto.class);
		
	}
	//delete a category
	public void deleteCategory(int id)
	{
		Category oldCat = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category"," id ", id));
		categoryRepository.delete(oldCat);
	}
	
	
}
