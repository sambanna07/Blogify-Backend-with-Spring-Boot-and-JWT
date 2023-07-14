package com.demo.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.blog.entity.User;
import com.demo.blog.exceptions.AlreadyExistException;
import com.demo.blog.exceptions.ResourceNotFoundException;
import com.demo.blog.exceptions.ServiceInternalException;
import com.demo.blog.payload.UserDTO;
import com.demo.blog.reposistories.UserRepo;
import com.demo.blog.services.UserService;

import lombok.extern.java.Log;

/**
 * 
 * @author Samundar Singh Rathore this contains all business logic related User
 */

@Service
@Log
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		boolean exists = this.userRepo.existsByEmail(userDTO.getEmail());
		if (exists) {
			throw new AlreadyExistException(601, "user", "email", userDTO.getEmail());
		}
		try {
			User user = this.userDTOToUser(userDTO); // convert to user object
			User savedUser = userRepo.save(user); // save to database
			return this.userToUserDTO(savedUser);
		} catch (Exception e) {
			throw new ServiceInternalException(603, "error in the user service:createUser method" + e.getMessage());
		}
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Integer userId) {
		// fetch user based on id,if user not available then throw exception
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(602, "user", "id", userId));

		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setAbout(userDTO.getAbout());

		User updatedUser = this.userRepo.save(user);
		return this.userToUserDTO(updatedUser);
	}

	@Override
	public UserDTO getUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(602, "user", "id", userId));
		return this.userToUserDTO(user);
	}

	@Override
	public List<UserDTO> getAllUser() {
		List<User> userList = this.userRepo.findAll();
		if (userList.size() == 0) {
			throw new ResourceNotFoundException(604, "users");
		}
		try {
			// convert user list to userdto list
			List<UserDTO> userDTOList = userList.stream().map(user -> this.userToUserDTO(user))
					.collect(Collectors.toList());
			return userDTOList;
		} catch (Exception e) {
			throw new ServiceInternalException(605, "error in the user service:createUser method" + e.getMessage());
		}
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(602, "User ", " id ", userId));
		try {
			this.userRepo.delete(user);
//		boolean alreadyexist=this.userRepo.existsById(userId);
		} catch (Exception e) {
			throw new ServiceInternalException(605, "error in the user service:deleteUser method" + e.getMessage());
		}

	}

	/**
	 * this method convert userdto type object to user type object
	 * 
	 * @param userDTO
	 * @return user object
	 * 
	 */
	public User userDTOToUser(UserDTO userDTO) {
		User user = modelMapper.map(userDTO, User.class);
//		User user=new User();
//		user.setId(userDTO.getId());
//		user.setName(userDTO.getName());
//		user.setEmail(userDTO.getEmail());
//		user.setPassword(userDTO.getPassword());
//		user.setAbout(userDTO.getAbout());

		return user;
	}

	/**
	 * this method convert the user object to userdto type object
	 * 
	 * @param user
	 * @return userDTO object
	 */
	public UserDTO userToUserDTO(User user) {

		UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);
//		UserDTO userDTO=new UserDTO();
//		userDTO.setId(user.getId());
//		userDTO.setName(user.getName());
//		userDTO.setEmail(user.getEmail());
//		userDTO.setPassword(user.getPassword());
//		userDTO.setAbout(user.getAbout());

		return userDTO;
	}

}
