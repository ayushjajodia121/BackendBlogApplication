package com.jajodia.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jajodia.blog.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	public List<Category> findByCategoryTitleContaining(String name );

}
