package com.jajodia.blog.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jajodia.blog.config.AppConstants;
import com.jajodia.blog.payload.ApiResponse;
import com.jajodia.blog.payload.PostDto;
import com.jajodia.blog.payload.PostResponse;
import com.jajodia.blog.service.PostService;

@RestController
@RequestMapping("/api/v1/post")
public class PostController 
{
	@Autowired
	private PostService postService;
	
	//************************************* CREATE POST API ********************************************//
	@PostMapping("/user/{userId}/categoryName/{categoryName}/savePost")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable int userId,@PathVariable String categoryName)
	{
		PostDto createdPostDto = postService.createPost(postDto,userId	,categoryName);
		return new ResponseEntity<PostDto>(createdPostDto,HttpStatus.CREATED);
	}
	
	//************************************** EDIT POST API **********************************************//
	
	@PutMapping("/updatePost/{postId}/{userId}/{categoryName}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,
			@PathVariable int postId,
			@PathVariable int userId,
			@PathVariable String categoryName)
	{
		PostDto updatedPostDto =  postService.updatePost(postDto, postId, categoryName, userId);
		return new ResponseEntity<PostDto>(updatedPostDto,HttpStatus.OK);
	}
	
	
	//*************************************** DELETE POST API *********************************************//
	
	@DeleteMapping("/deletePostById/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable int postId)
	{
		postService.deletePost(postId);
//		return new ResponseEntity(Map.of("message","User Deleted Successfully"),HttpStatus.OK);
		return new ResponseEntity<ApiResponse>(new ApiResponse("post Deleted Successfully",true),HttpStatus.OK);
	}
	
	//*************************************** GET POST BY ID ************************************************//
	
	@GetMapping("fetchPostById/{postId}")
	public ResponseEntity<PostDto> fetchPostById(@PathVariable int postId )
	{
		PostDto postById = postService.fetchPostById(postId);
		return new ResponseEntity<PostDto>(postById,HttpStatus.OK);
	}
	
	//**************************************** GET ALL POSTS ************************************************//
	@GetMapping("/fetchAllPosts")
	public ResponseEntity<PostResponse> fetchAllPosts(
				@RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required=false) Integer pageNumber,
				@RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE,required=false) Integer pageSize,
				@RequestParam(value="sortBy",defaultValue = AppConstants.SORT_BY,required=false) String sortBy,
				@RequestParam(value="sortDir",defaultValue = AppConstants.SORT_DIR,required=false) String sortDir
			)
	{
		PostResponse allPosts = postService.fetchAllPosts(pageSize,pageNumber,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(allPosts,HttpStatus.OK);
	}
	
	//***************************************** GET ALL POSTS OF AN USER ***************************************//
	@GetMapping("/fetchPostByUser/{userId}")
	public ResponseEntity<PostResponse> fetchPostByUser(@PathVariable int userId,
			@RequestParam(value="pageNumber",defaultValue = "0",required=false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue = "5",required=false) Integer pageSize
			)
	{
		PostResponse postsByUser = postService.fetchPostsByUser(userId,pageSize,pageNumber);
		return new ResponseEntity<PostResponse>(postsByUser,HttpStatus.OK);
	}
	
	//***************************************** GET ALL POSTS OF A CATEGORY *******************************************//
	@GetMapping("/fetchPostByCategory/{categoryId}")
	public ResponseEntity<PostResponse> fetchPostByCategory(@PathVariable int categoryId,
			@RequestParam(value="pageNumber",defaultValue = "0",required=false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue = "5",required=false) Integer pageSize
			)
	{
		PostResponse postsByCategory = postService.fetchPostsByCategory(categoryId,pageSize,pageNumber);
		return new ResponseEntity<PostResponse>(postsByCategory,HttpStatus.OK);
	}
	
	//***************************************** SEARCH POSTS BY NAME OF TITLE ******************************************//
	@GetMapping("/fetchPostsByName/{keyword}")
	public ResponseEntity<PostResponse> fetchPostByName(@PathVariable String keyword,
			@RequestParam(value="pageNumber",defaultValue = "0",required=false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue = "5",required=false) Integer pageSize
			)
	{
		PostResponse postsByName = postService.fetchByKeyword(keyword,pageSize,pageNumber);
		return new ResponseEntity<PostResponse>(postsByName,HttpStatus.OK);
	}
}
