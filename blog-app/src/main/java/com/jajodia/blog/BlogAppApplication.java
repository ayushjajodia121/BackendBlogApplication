package com.jajodia.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.jajodia.blog.config.AppConstants;
import com.jajodia.blog.model.Role;
import com.jajodia.blog.repository.RoleRepo;

//import io.swagger.v3.oas.annotations.OpenAPIDefinition;
//import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
//import io.swagger.v3.oas.annotations.info.Contact;
//import io.swagger.v3.oas.annotations.info.Info;
//import io.swagger.v3.oas.annotations.info.License;
//import io.swagger.v3.oas.annotations.security.SecurityScheme;
//import io.swagger.v3.oas.annotations.servers.Server;

@EnableWebMvc
@SpringBootApplication
//@SecurityScheme(
//		  name = "Bearer Authentication",
//		  type = SecuritySchemeType.HTTP,
//		  bearerFormat = "JWT",
//		  scheme = "bearer"
//		)
//@OpenAPIDefinition(
//		  info = @Info(
//		    title = "Blogging Backend Application",
//		    version = "${1.0.0}",
//		    contact = @Contact(
//		      name = "Ayush Jajodia", email = "ayushjajodia121@gmail.com", url = "https://www.baeldung.com"
//		    ),
//		    license = @License(
//		      name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"
//		    ),
//		    termsOfService = "${tos.uri}",
//		    description = "User,categories,Posts related api for  creating a blog application"
//		  )
//		  servers = @Server(
//		    url = "http:localhost:8086/",
//		    description = "Production"
//		  )
		//)
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
			Role role1 = this.roleRepo.save(roleNormal);
			Role roleAdmin = new Role();
			roleAdmin.setId(AppConstants.ROLE_ADMIN);
			roleAdmin.setName("ROLE_ADMIN");
			Role role2 = this.roleRepo.save(roleAdmin);
			System.out.println(passwordEncoder.encode("test@123"));
			
//			List<Role> roles = List.of(roleNormal,roleAdmin);
//			List<Role> result = this.roleRepo.saveAll(roles);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
