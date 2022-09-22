package com.jajodia.blog.service;

import java.util.List;

import com.jajodia.blog.payload.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto, int postId);
	
	void deleteComment(int commentId);
	
	List<CommentDto> fetchAllCommentsOnPost(int postId);

}
