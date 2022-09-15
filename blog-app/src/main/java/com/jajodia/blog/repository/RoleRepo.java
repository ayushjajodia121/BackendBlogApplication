package com.jajodia.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jajodia.blog.model.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
