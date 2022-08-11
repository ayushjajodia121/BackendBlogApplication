package com.jajodia.blog.service;

import java.util.List;

import com.jajodia.blog.model.Post;
import com.jajodia.blog.payload.CategoryDto;
import com.jajodia.blog.payload.PostDto;
import com.jajodia.blog.payload.UserDto;

public interface PostService {
	
	//create post
	PostDto createPost(PostDto postDto,int categoryId,int userId);
	
	//edit post
	PostDto updatePost(PostDto postDto,int postId,int categoryId,int userId);
	
	//delete post
	void deletePost(int id);
	
	//get post
	PostDto fetchPostById(int id);
	
	//get All posts
	List<PostDto> fetchAllPosts();
	
	//get all post by user
	List<PostDto> fetchPostsByUser(int userId);
	
	//get all post by category
	List<PostDto> fetchPostsByCategory(int categoryId);
	
	//search posts by title name
	List<PostDto> fetchByKeyword(String keyword);

}
