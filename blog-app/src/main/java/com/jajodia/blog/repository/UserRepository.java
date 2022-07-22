package com.jajodia.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jajodia.blog.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
