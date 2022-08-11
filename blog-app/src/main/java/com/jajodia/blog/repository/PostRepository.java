package com.jajodia.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jajodia.blog.model.Category;
import com.jajodia.blog.model.Post;
import com.jajodia.blog.model.User;

public interface PostRepository extends JpaRepository<Post, Integer> 
{
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	List<Post> findByTitleContaining(String name);
}
