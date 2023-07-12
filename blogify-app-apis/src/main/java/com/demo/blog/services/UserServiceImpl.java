package com.demo.blog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.blog.entity.User;
import com.demo.blog.exceptions.AlreadyExistException;
import com.demo.blog.exceptions.ResourceNotFoundException;
import com.demo.blog.payload.UserDTO;
import com.demo.blog.reposistories.UserRepo;

import lombok.extern.java.Log;

/**
 * 
 * @author Samundar Singh Rathore
 * this contains all business logic related User
 */

@Service
@Log
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		boolean exists=this.userRepo.existsByEmail(userDTO.getEmail());
		if(exists) {
			throw new AlreadyExistException("user","email",userDTO.getEmail());
		}
		log.info("email"+exists);
		User user=this.userDTOToUser(userDTO); //convert to user object
		User savedUser=userRepo.save(user);  //save to database
		return this.userToUserDTO(savedUser);
		
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO,Integer userId) {
		//fetch user based on id,if user not available then throw exception
		User user=this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user","id",userId));
		
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setAbout(userDTO.getAbout());
		
		User updatedUser=this.userRepo.save(user);
		return this.userToUserDTO(updatedUser);
	}

	@Override
	public UserDTO getUserById(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow( () -> new ResourceNotFoundException("user", "id", userId));	
		return this.userToUserDTO(user);
	}

	@Override
	public List<UserDTO> getAllUser() {
		List<User> userList=this.userRepo.findAll();
		//convert user list to userdto list
		List<UserDTO> userDTOList= userList.stream().map( user -> this.userToUserDTO(user))
				.collect(Collectors.toList());
		return userDTOList;
	}
	
	@Override
	public void deleteUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User "," id ", userId));
		this.userRepo.delete(user);
//		boolean alreadyexist=this.userRepo.existsById(userId);
		
	}

	
	/**
	 * this method convert userdto type object to user type object
	 * @param userDTO
	 * @return user object
	 * 
	 */
	public User userDTOToUser(UserDTO userDTO) {
		User user=new User();
		user.setId(userDTO.getId());
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setAbout(userDTO.getAbout());
		
		return user;
	}
	
	/**
	 * this method convert the user object to userdto type object
	 * @param user
	 * @return userDTO object
	 */
	public UserDTO userToUserDTO(User user) {
		UserDTO userDTO=new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setEmail(user.getEmail());
		userDTO.setPassword(user.getPassword());
		userDTO.setAbout(user.getAbout());
		
		return userDTO;
	}


}
