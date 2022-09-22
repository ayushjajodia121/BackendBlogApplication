package com.jajodia.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jajodia.blog.payload.ApiResponse;
import com.jajodia.blog.payload.CommentDto;
import com.jajodia.blog.service.CommentService;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	//********************************************* CREATE A COMMENT *******************************************************//
	@PostMapping("/saveComment/post/{postId}")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable int postId)
	{
		CommentDto createdCommentDto = this.commentService.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(createdCommentDto,HttpStatus.CREATED);
	};
	//****************************************** FETCH COMMENTS OF A POST **************************************************//
	
	@GetMapping("/fetchComments/post/{postId}")
	public ResponseEntity<List<CommentDto>> fetchCommentsPost(@PathVariable int postId)
	{
		List<CommentDto> commentsDto = this.commentService.fetchAllCommentsOnPost(postId);
		return new ResponseEntity<List<CommentDto>>(commentsDto,HttpStatus.OK);
	}
	//*********************************************** DELETE A COMMENT *****************************************************//
	@DeleteMapping("/deleteComment/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable int commentId)
	{
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("comment Deleted Successfully",true),HttpStatus.OK);
	}
}
