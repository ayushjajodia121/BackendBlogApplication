package com.jajodia.blog.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jajodia.blog.model.Category;
import com.jajodia.blog.model.Post;
import com.jajodia.blog.model.User;

public interface PostRepository extends JpaRepository<Post, Integer> 
{
	Page<Post> findByUser(User user,Pageable p);
	Page<Post> findByCategory(Category category,Pageable p);
	Page<Post> findByTitleContaining(String name,Pageable p);
}
