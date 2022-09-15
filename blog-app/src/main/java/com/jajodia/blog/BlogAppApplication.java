package com.jajodia.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jajodia.blog.config.AppConstants;
import com.jajodia.blog.model.Role;
import com.jajodia.blog.repository.RoleRepo;

@SpringBootApplication
public class BlogAppApplication implements CommandLineRunner {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}


	@Override
	public void run(String... args) throws Exception {
		
		try {
			
			Role roleNormal = new Role();
			roleNormal.setId(AppConstants.ROLE_NORMAL);
			roleNormal.setName("ROLE_NORMAL");
			
			Role roleAdmin = new Role();
			roleAdmin.setId(AppConstants.ROLE_ADMIN);
			roleAdmin.setName("ROLE_ADMIN");
			
			List<Role> roles = List.of(roleNormal,roleAdmin);
			List<Role> result = this.roleRepo.saveAll(roles);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
