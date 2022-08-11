package com.jajodia.blog.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jajodia.blog.exception.ResourceNotFoundException;
import com.jajodia.blog.model.Category;
import com.jajodia.blog.model.Post;
import com.jajodia.blog.model.User;
import com.jajodia.blog.payload.PostDto;
import com.jajodia.blog.repository.CategoryRepository;
import com.jajodia.blog.repository.PostRepository;
import com.jajodia.blog.repository.UserRepository;
import com.jajodia.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	//************************************** CREATE POST *****************************************//
	@Override
	public PostDto createPost(PostDto postDto,int userId,int categoryId) 
	{
		Post post = modelMapper.map(postDto, Post.class);
		User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","user_id",userId));
		Category category= categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","category_id",categoryId));
		
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		
		post.setUser(user);
		post.setCategory(category);
		Post savedPost = postRepository.save(post);
		return modelMapper.map(savedPost, PostDto.class);
	}

	//************************************* UPDATE POST ********************************************// 
	@Override
	public PostDto updatePost(PostDto postDto, int postId,int categoryId,int userId) 
	{
		Post oldPost = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId: ",postId));
		Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId: ",categoryId));
		User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","userId: ",userId));
		
		oldPost.setTitle(postDto.getTitle());
		oldPost.setCategory(category);
		oldPost.setContent(postDto.getContent());
		oldPost.setImageName(postDto.getImageName());
		oldPost.setUser(user);
		oldPost.setAddedDate(new Date());
		
		PostDto newPostDto = modelMapper.map(oldPost, PostDto.class);
		
		return newPostDto;
	}

	//************************************* DELETE POST BY ID *****************************************//
	@Override
	public void deletePost(int postId) {
		Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId: ",postId));
		postRepository.delete(post);
	}

	//************************************* GET POST BY ID ********************************************//
	@Override
	public PostDto fetchPostById(int postId) {
		Post post =  postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","PostId: ",postId));
		PostDto postDto = modelMapper.map(post, PostDto.class);
		return postDto;
	}

	//************************************* GET ALL POSTS **********************************************//
	@Override
	public List<PostDto> fetchAllPosts() 
	{
		List<Post> posts = postRepository.findAll();
		List<PostDto> postDtos = posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}
	
	//************************************* GET POSTS BY A USER ****************************************//
	@Override
	public List<PostDto> fetchPostsByUser(int userId) 
	{
		User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("Uer","CategoryId: ",userId));
		List<Post> posts = postRepository.findByUser(user); 
		List<PostDto> postsByUser = posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postsByUser;
	}

	//************************************* GET POSTS BY CATEGORY ****************************************//
	@Override
	public List<PostDto> fetchPostsByCategory(int categoryId) 
	{
		Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId: ",categoryId));
		List<Post> posts = postRepository.findByCategory(category); 
		List<PostDto> postsByCategory = posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postsByCategory;
	}

	//************************************* SEARCH POSTS BY NAME OF TITLE *********************************//
	@Override
	public List<PostDto> fetchByKeyword(String keyword) {
		List<Post> posts = postRepository.findByTitleContaining(keyword);
		List<PostDto> postsByName = posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postsByName;
	}

}
