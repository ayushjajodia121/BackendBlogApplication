package com.jajodia.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jajodia.blog.model.Comment;
import com.jajodia.blog.model.Post;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{
	
	List<Comment> findByPost(Post post);

}
