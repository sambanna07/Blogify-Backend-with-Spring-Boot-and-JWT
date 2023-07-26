package com.demo.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.demo.blog.configurations.AppConstants;
import com.demo.blog.entity.Role;
import com.demo.blog.exceptions.ResourceNotFoundException;
import com.demo.blog.reposistories.RoleRepo;

@SpringBootApplication
public class BlogifyAppApisApplication implements CommandLineRunner{
	
	@Autowired
	private RoleRepo roleRepo;
	

	public static void main(String[] args) {
		SpringApplication.run(BlogifyAppApisApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			
			//for admin
			Role role=new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setRole("ROLE_ADMIN");
			
			//for normal
			Role role1=new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setRole("ROLE_NORMAL");
			
			List<Role> roles = List.of(role,role1);
			
			this.roleRepo.saveAll(roles);

		} catch (Exception e) {
			throw new ResourceNotFoundException(1111,"some thing went wrong in creating roles table");
		}
		
	}


}
