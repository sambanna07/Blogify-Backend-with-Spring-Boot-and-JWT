package com.demo.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * UserController for handling all user related requests
 * @author Samundar Singh Rathore
 *
 */

import com.demo.blog.payload.UserDTO;
import com.demo.blog.services.UserService;
@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/")
	public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO){
		UserDTO createUserDTO= this.userService.createUser(userDTO);
		return new ResponseEntity<>(createUserDTO,HttpStatus.CREATED);
	}

}
