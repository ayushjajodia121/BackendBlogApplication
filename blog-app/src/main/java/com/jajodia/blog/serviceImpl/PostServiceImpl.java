package com.jajodia.blog.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jajodia.blog.exception.CategoryNotFoundException;
import com.jajodia.blog.exception.ResourceNotFoundException;
import com.jajodia.blog.model.Category;
import com.jajodia.blog.model.Post;
import com.jajodia.blog.model.User;
import com.jajodia.blog.payload.PostDto;
import com.jajodia.blog.payload.PostResponse;
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
	public PostDto createPost(PostDto postDto,int userId,String categoryName) 
	{
		Post post = modelMapper.map(postDto, Post.class);
		User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","user_id",userId));
		Category category= categoryRepository.findByCategoryTitle(categoryName);
		if(category!=null ) {
		
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		
		post.setUser(user);
		post.setCategory(category);
		Post savedPost = postRepository.save(post);
		return modelMapper.map(savedPost, PostDto.class);
		}else {
			throw new CategoryNotFoundException("Category","CategoryName: ",categoryName);
		}
	}

	//************************************* UPDATE POST ********************************************// 
	@Override
	public PostDto updatePost(PostDto postDto, int postId, int userId, String categoryName) 
	{
		Post oldPost = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId: ",postId));
	//	Category oldPostCategory = oldPost.getCategory();  get category assigned to particular 
		User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","userId: ",userId));
		Category category = categoryRepository.findByCategoryTitle(categoryName);
		if(category != null)
		{	
			oldPost.setTitle(postDto.getTitle());
			oldPost.setCategory(category);
			oldPost.setContent(postDto.getContent());
			oldPost.setImageName(postDto.getImageName());
			oldPost.setUser(user);
			oldPost.setAddedDate(new Date());
			Post updatedPost = postRepository.save(oldPost);
			PostDto newPostDto = modelMapper.map(updatedPost, PostDto.class);
			return newPostDto;
		}
		else {
			throw new CategoryNotFoundException("Category","CategoryName: ",categoryName);
		}
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
	public PostResponse fetchAllPosts(int pageSize,int pageNumber,String sortBy,String sortDir) 
	{
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc"))
		{
			sort = Sort.by(sortBy).ascending();
		}else {
			sort = Sort.by(sortBy).descending();
		}
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePosts = postRepository.findAll(p);
		List<Post> posts = pagePosts.getContent();
		List<PostDto> postDtos = posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse resp = new PostResponse();
		resp.setPosts(postDtos);
		resp.setPageNumber(pagePosts.getNumber());
		resp.setPageSize(pagePosts.getSize());
		resp.setTotalElements((int)pagePosts.getTotalElements());
		resp.setTotalPages(pagePosts.getTotalPages());
		resp.setLastPage(pagePosts.isLast());
		return resp;
	}
	
	//************************************* GET POSTS BY A USER ****************************************//
	@Override
	public PostResponse fetchPostsByUser(int userId,int pageSize,int pageNumber) 
	{
		Pageable p = PageRequest.of(pageNumber, pageSize);
		User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("Uer","CategoryId: ",userId));
		Page<Post> pagePosts = postRepository.findByUser(user,p); 
		List<Post> posts = pagePosts.getContent();
		List<PostDto> postsByUser = posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse resp = new PostResponse();
		
		resp.setPosts(postsByUser);
		resp.setPageNumber(pagePosts.getNumber());
		resp.setPageSize(pagePosts.getSize());
		resp.setTotalElements((int)pagePosts.getTotalElements());
		resp.setTotalPages(pagePosts.getTotalPages());
		resp.setLastPage(pagePosts.isLast());
		return resp;
	}

	//************************************* GET POSTS BY CATEGORY ****************************************//
	@Override
	public PostResponse fetchPostsByCategory(int categoryId,int pageSize,int pageNumber) 
	{
		Pageable p = PageRequest.of(pageNumber, pageSize);
		Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId: ",categoryId));
		Page<Post> pagePosts = postRepository.findByCategory(category,p); 
		List<Post> posts = pagePosts.getContent();
		List<PostDto> postsByCategory = posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse resp = new PostResponse();
		
		resp.setPosts(postsByCategory);
		resp.setPageNumber(pagePosts.getNumber());
		resp.setPageSize(pagePosts.getSize());
		resp.setTotalElements((int)pagePosts.getTotalElements());
		resp.setTotalPages(pagePosts.getTotalPages());
		resp.setLastPage(pagePosts.isLast());
		
		return resp;
	}

	//************************************* SEARCH POSTS BY NAME OF TITLE *********************************//
	@Override
	public PostResponse fetchByKeyword(String keyword,int pageSize,int pageNumber) {
		
		Pageable p = PageRequest.of(pageNumber, pageSize);
		
		Page<Post> pagePosts = postRepository.findByTitleContaining(keyword,p);
		List<Post> posts = pagePosts.getContent();
		List<PostDto> postsByName = posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse resp = new PostResponse();
		
		resp.setPosts(postsByName);
		resp.setPageNumber(pagePosts.getNumber());
		resp.setPageSize(pagePosts.getSize());
		resp.setTotalElements((int)pagePosts.getTotalElements());
		resp.setTotalPages(pagePosts.getTotalPages());
		resp.setLastPage(pagePosts.isLast());
		
		return resp;
	}


}
