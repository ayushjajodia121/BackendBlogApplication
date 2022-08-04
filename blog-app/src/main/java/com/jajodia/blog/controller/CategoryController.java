package com.jajodia.blog.controller;

import java.util.List;

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
import com.jajodia.blog.payload.CategoryDto;
import com.jajodia.blog.service.CategoryService;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	//get All categories
	@GetMapping("/fetchAll")
	public ResponseEntity<List<CategoryDto>> fetchAllCategories()
	{
		List<CategoryDto> categoryDtos = categoryService.getAllCategory();
		return new ResponseEntity<List<CategoryDto>>(categoryDtos,HttpStatus.OK);
	}

	//get category by ID
	@GetMapping("/fetchCategoryById/{id}")
	public ResponseEntity<CategoryDto> fetchCategoryById(@PathVariable int id)
	{
		CategoryDto catDto = categoryService.getCatgoryById(id);
		return new ResponseEntity<CategoryDto>(catDto,HttpStatus.OK);
	}
	
	//get categories by name
	@GetMapping("/categoriesByName/{name}")
	public ResponseEntity<List<CategoryDto>> fetchByCategoryName(@PathVariable String name)
	{
		List<CategoryDto> categoryDtos = categoryService.getCategoryByTitle(name);
		return new ResponseEntity<List<CategoryDto>>(categoryDtos,HttpStatus.OK);
	}
	
	//create category
	@PostMapping("/createCategory")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto)
	{
		CategoryDto createdCategoryDto = categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createdCategoryDto,HttpStatus.CREATED);
	}
	
	//update Category by id
	@PutMapping("/updateCategory/{id}")
	public ResponseEntity<CategoryDto> updatCategory(@RequestBody CategoryDto categoryDto,@PathVariable int id)
	{
		CategoryDto updatedCategoryDto = categoryService.updateCategory(categoryDto, id);
		return new ResponseEntity<CategoryDto>(updatedCategoryDto,HttpStatus.OK);
	}
	
	//delete Category by id
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable int id)
	{
		categoryService.deleteCategory(id);
//		return new ResponseEntity(Map.of("message","User Deleted Successfully"),HttpStatus.OK);
		return new ResponseEntity<ApiResponse>(new ApiResponse("category Deleted Successfully",true),HttpStatus.OK);
	}
}
