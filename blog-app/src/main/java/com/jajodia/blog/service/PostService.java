package com.jajodia.blog.service;


import com.jajodia.blog.payload.PostDto;
import com.jajodia.blog.payload.PostResponse;

public interface PostService {
	
	//create post
	PostDto createPost(PostDto postDto,int userId,String categoryName);
	
	//edit post
	PostDto updatePost(PostDto postDto, int postId, String categoryName, int userId);
	
	//delete post
	void deletePost(int id);
	
	//get post
	PostDto fetchPostById(int id);
	
	//get All posts
	PostResponse fetchAllPosts(int pageSize,int pageNumber, String sortBy, String sortDir);
	
	//get all post by user
	PostResponse fetchPostsByUser(int userId,int pageSize,int pageNumber);
	
	//get all post by category
	PostResponse fetchPostsByCategory(int categoryId,int pageSize,int pageNumber);
	
	//search posts by title name
	PostResponse fetchByKeyword(String keyword,int pageSize,int pageNumber);


}
