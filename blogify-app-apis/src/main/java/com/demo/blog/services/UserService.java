package com.demo.blog.services;
import java.util.List;

import com.demo.blog.payload.UserDTO;
/**
 * 
 * @author Samundar Singh Rathore
 * this is interface for user related business logic
 *
 */
public interface UserService {
	
	/**
	 * for create new user
	 * @param userDTO
	 * @return userDTO object
	 */
	 UserDTO createUser(UserDTO userDTO);
	 
	 /**
	  * for update the based on id
	  * @param userDTO,userId
	  * @return use object
	  */
	 UserDTO updateUser(UserDTO userDTO,Integer userId);
	 
	 /**
	  * for getting user based on id
	  * @param userId
	  * @return Use object
	  */
	 UserDTO getUserById(Integer userId);
	 
	 /**
	  * 
	  * @return List of user
	  */
	 List<UserDTO> getAllUser();
	 
	 /**
	  * for deleting the user based on id
	  * @param userId
	  */
	 void deleteUser(Integer userId);

}
