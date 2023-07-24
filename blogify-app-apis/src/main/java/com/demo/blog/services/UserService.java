package com.demo.blog.services;
import java.util.List;

import com.demo.blog.payload.UserDTO;
import com.demo.blog.payload.UserResponse;
import com.demo.blog.payload.UserResponseDTO;
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
	 UserResponseDTO createUser(UserDTO userDTO);
	 
	 /**
	  * for register the user
	  * @param user
	  * @return
	  */
	 UserResponseDTO registerNewUser(UserDTO user);
	 
	 /**
	  * for update the based on id
	  * @param userDTO,userId
	  * @return use UserDTO object
	  */
	 UserResponseDTO updateUser(UserDTO userDTO,Integer userId);
	 
	 /**
	  * for getting user based on id
	  * @param userId
	  * @return Use object
	  */
	 UserResponseDTO getUserById(Integer userId);
	 
	 /**
	  * 
	  * @return List of user
	  */
	 UserResponse getAllUser(Integer pageNumber,Integer numberOfElements,String sortBy,String sortDir);
	 
	 /**
	  * for deleting the user based on id
	  * @param userId
	  */
	 void deleteUser(Integer userId);
	 
	 /**
	  * searching the user based on username
	  * @param name
	  * @return list of userdtos
	  */
	 List<UserResponseDTO> findByNameContaining(String name);

}
