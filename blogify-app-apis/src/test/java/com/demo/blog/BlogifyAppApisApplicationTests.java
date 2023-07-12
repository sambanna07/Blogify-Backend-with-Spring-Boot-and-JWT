package com.demo.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.blog.reposistories.UserRepo;

import lombok.extern.java.Log;

@SpringBootTest
@Log
class BlogifyAppApisApplicationTests {
	
	
	@Autowired
	private UserRepo userRepo;

	@Test
	void contextLoads() {
	}
	
	
	@Test
	void repoTest() {
		String className=this.userRepo.getClass().getName();
		String packageName=this.userRepo.getClass().getPackageName();
		log.info(className);
		log.info(packageName);
		
	}

}
