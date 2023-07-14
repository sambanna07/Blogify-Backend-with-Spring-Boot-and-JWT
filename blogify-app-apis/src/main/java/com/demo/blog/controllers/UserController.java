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
	
	/**
	 * EndPoint for create user resource
	 * @param userDTO
	 * @return newly created user resource with id
	 */
	@PostMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO)throws Exception{
		UserDTO createUserDTO= this.userService.createUser(userDTO);
		return new ResponseEntity<UserDTO>(createUserDTO,HttpStatus.CREATED);
	}
	
	/**
	 * EndPoint for getting user by id
	 * @param userId
	 * @return user resource based on provided userId
	 */
	@GetMapping(value = "/{id}/",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> getUserById(@PathVariable(value="id") Integer userId)throws Exception{
		UserDTO userById= this.userService.getUserById(userId);
		return new ResponseEntity<UserDTO>(userById,HttpStatus.OK);
	}
	
	/**
	 * EndPoint for getting all user resource
	 * @return list of user resource
	 */
	@GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserDTO>> getUsers()throws Exception{
		List<UserDTO> userList = this.userService.getAllUser();
		return ResponseEntity.ok(userList); //direct return
	}
	
	/**
	 * EndPoint for update the user resource based on userId
	 * @param userDTO
	 * @param userId
	 * @return updated user resource
	 */
	@PutMapping(value="/update",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO,@RequestParam(value = "id") Integer userId)throws Exception{
		UserDTO updatedUser = this.userService.updateUser(userDTO, userId);
		return new ResponseEntity<UserDTO>(updatedUser,HttpStatus.OK);
	}
	
	/**
	 * EndPoint for delete user resource based on userId
	 * @param userId
	 * @return success message = user deleted successfully
	 */
	@DeleteMapping(value="",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteUserById(@RequestParam(value = "id") Integer userId)throws Exception{
		this.userService.deleteUser(userId);
		return new ResponseEntity<String>("user deleted successfully",HttpStatus.ACCEPTED);
	}

}
