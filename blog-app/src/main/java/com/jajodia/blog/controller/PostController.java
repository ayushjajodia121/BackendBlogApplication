package com.jajodia.blog.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.jajodia.blog.config.AppConstants;
import com.jajodia.blog.payload.ApiResponse;
import com.jajodia.blog.payload.PostDto;
import com.jajodia.blog.payload.PostResponse;
import com.jajodia.blog.service.FileService;
import com.jajodia.blog.service.PostService;


@RestController
@RequestMapping("/api/v1/post")
public class PostController 
{
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
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
		PostDto updatedPostDto =  postService.updatePost(postDto, postId, userId, categoryName);
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
	
	//****************************************** SAVE POST IMAGE ************************************************************//
	@PostMapping("image/upload/{postId}/{userId}/{categoryName}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
				@PathVariable int postId,
				@PathVariable int userId,
				@PathVariable String categoryName
			) throws IOException{
		String imageName = this.fileService.uploadImage(path, image);
		PostDto postDtoById = this.postService.fetchPostById(postId);
		postDtoById.setImageName(imageName);
		PostDto updatedPost = postService.updatePost(postDtoById, postId, userId, categoryName);
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	}
	
	//******************************************** METHOD TO SERVE IMAGE FILES ************************************************//
	
	@GetMapping(value="/profiles/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
				@PathVariable("imageName") String imageName,
				HttpServletResponse response
			) throws IOException {
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
	}
}
