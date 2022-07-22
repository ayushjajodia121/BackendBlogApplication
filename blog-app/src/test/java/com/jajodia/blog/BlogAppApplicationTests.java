package com.jajodia.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jajodia.blog.repository.UserRepository;
import com.jajodia.blog.service.UserService;

@SpringBootTest
class BlogAppApplicationTests {
	
	@Autowired
	private UserRepository userRepository;

	
	@Autowired
	private UserService userService;
	
	@Test
	void contextLoads() {
	}
	
//	@Test
//	public void repoTest()
//	{
//		String repoclassName = this.userRepository.getClass().getName();
//		String repopackName = this.userRepository.getClass().getPackageName();
//		System.out.println(repoclassName );
//		System.out.println(repopackName );
//	}
	@Test
	public void serviceTest()
	{
		String className = this.userService.getClass().getName();
		String packName = this.userService.getClass().getPackageName();
		System.out.println(className);
		System.out.println(packName);
	}

}
