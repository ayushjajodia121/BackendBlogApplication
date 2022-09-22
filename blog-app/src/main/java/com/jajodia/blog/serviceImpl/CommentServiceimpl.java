package com.jajodia.blog.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jajodia.blog.exception.ResourceNotFoundException;
import com.jajodia.blog.model.Comment;
import com.jajodia.blog.model.Post;
import com.jajodia.blog.payload.CommentDto;
import com.jajodia.blog.repository.CommentRepository;
import com.jajodia.blog.repository.PostRepository;
import com.jajodia.blog.service.CommentService;

@Service
public class CommentServiceimpl implements CommentService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;

	@Override
	public CommentDto createComment(CommentDto commentDto, int postId) {
		Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post with ","post_id",postId));
		Comment comment = modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment = this.commentRepository.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(int commentId) {
		Comment com = this.commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment with ","commentId",commentId));
		this.commentRepository.delete(com);
	}

	@Override
	public List<CommentDto> fetchAllCommentsOnPost(int postId) {
		Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post with ","postID",postId));
		List<Comment> comments = this.commentRepository.findByPost(post);
		List<CommentDto> commentDtos = comments.stream().map((comment)->modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
		return commentDtos;
	}

}
