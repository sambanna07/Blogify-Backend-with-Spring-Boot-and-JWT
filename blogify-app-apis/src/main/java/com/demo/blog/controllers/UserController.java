package com.demo.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * UserController for handling all user related requests
 * @author Samundar Singh Rathore
 *
 */

import com.demo.blog.payload.UserDTO;
import com.demo.blog.services.UserService;
@RestController
@RequestMapping(value ="/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//create new user
	@PostMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO){
		UserDTO createUserDTO= this.userService.createUser(userDTO);
		return new ResponseEntity<>(createUserDTO,HttpStatus.CREATED);
	}
	
	//get user by id
	@GetMapping(value = "/{id}/",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserById(@PathVariable(value="id") Integer userId){
		UserDTO userById= this.userService.getUserById(userId);
		return new ResponseEntity<>(userById,HttpStatus.OK);
	}
	
	//get all user
	@GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUsers(){
		List<UserDTO> userList = this.userService.getAllUser();
		return ResponseEntity.ok(userList); //direct return
	}
	
	//update user base on id
	@PutMapping(value="/update",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUser(@Valid @RequestBody UserDTO userDTO,@RequestParam(value = "id") Integer userId){
		UserDTO updatedUser = this.userService.updateUser(userDTO, userId);
		return new ResponseEntity<>(updatedUser,HttpStatus.OK);
	}
	
	//delete user based on id
	@DeleteMapping(value="",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteUserById(@RequestParam(value = "id") Integer userId){
		this.userService.deleteUser(userId);
		return new ResponseEntity<>("deleted",HttpStatus.ACCEPTED);
	}

}
