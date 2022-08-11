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
import com.jajodia.blog.payload.PostDto;
import com.jajodia.blog.service.PostService;

@RestController
@RequestMapping("/api/v1/post")
public class PostController 
{
	@Autowired
	private PostService postService;
	
	//create post api
	@PostMapping("/user/{userId}/categoryId/{categoryId}/savePost")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable int userId,@PathVariable int categoryId)
	{
		PostDto createdPostDto = postService.createPost(postDto,userId	,categoryId);
		return new ResponseEntity<PostDto>(createdPostDto,HttpStatus.CREATED);
	}
	//edit post api
	@PutMapping("/updatePost/{postId}/{userId}/{categoryId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable int postId,@PathVariable int userId,@PathVariable int categoryId)
	{
		PostDto updatedPostDto =  postService.updatePost(postDto, postId, categoryId, userId);
	}
	
	
	//delete post
	@DeleteMapping("/deletePostById/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable int postId)
	{
		postService.deletePost(postId);
//		return new ResponseEntity(Map.of("message","User Deleted Successfully"),HttpStatus.OK);
		return new ResponseEntity<ApiResponse>(new ApiResponse("post Deleted Successfully",true),HttpStatus.OK);
	}
	
	//get posts By ID
	@GetMapping("fetchPostById/{postId}")
	public ResponseEntity<PostDto> fetchPostById(@PathVariable int postId )
	{
		PostDto postById = postService.fetchPostById(postId);
		return new ResponseEntity<PostDto>(postById,HttpStatus.OK);
	}
	
	//get all posts
	@GetMapping("/fetchAllPosts")
	public ResponseEntity<List<PostDto>> fetchAllPosts()
	{
		List<PostDto> postDtos = postService.fetchAllPosts();
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	//get all post of an user
	@GetMapping("/fetchPostByUser/{userId}")
	public ResponseEntity<List<PostDto>> fetchPostByUser(@PathVariable int userId)
	{
		List<PostDto> postsByUser = postService.fetchPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(postsByUser,HttpStatus.OK);
	}
	
	//get all post related to a category
	@GetMapping("/fetchPostByCategory/{categoryId}")
	public ResponseEntity<List<PostDto>> fetchPostByCategory(@PathVariable int categoryId)
	{
		List<PostDto> postsByCategory = postService.fetchPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(postsByCategory,HttpStatus.OK);
	}
	
	//get All posts by name of title
	@GetMapping("/fetchPostsByName/{keyword}")
	public ResponseEntity<List<PostDto>> fetchPostByName(@PathVariable String keyword)
	{
		List<PostDto> postsByName = postService.fetchByKeyword(keyword);
		return new ResponseEntity<List<PostDto>>(postsByName,HttpStatus.OK);
	}
}
