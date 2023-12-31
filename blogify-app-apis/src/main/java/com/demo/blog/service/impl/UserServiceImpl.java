package com.demo.blog.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.blog.configurations.AppConstants;
import com.demo.blog.entity.Role;
import com.demo.blog.entity.User;
import com.demo.blog.exceptions.AlreadyExistException;
import com.demo.blog.exceptions.ResourceNotFoundException;
import com.demo.blog.exceptions.ServiceInternalException;
import com.demo.blog.payload.UserDTO;
import com.demo.blog.payload.UserResponse;
import com.demo.blog.payload.UserResponseDTO;
import com.demo.blog.reposistories.RoleRepo;
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserResponseDTO createUser(UserDTO userDTO) {
		boolean exists = this.userRepo.existsByEmail(userDTO.getEmail());
		if (exists) {
			throw new AlreadyExistException(601, "user", "email", userDTO.getEmail());
		}
		try {
			User user = this.userDTOToUser(userDTO); // convert to user object
			User savedUser = userRepo.save(user); // save to database
			return this.userToUserResponseDTO(savedUser);
		} catch (Exception e) {
			throw new ServiceInternalException(611, "error in the user service:createUser method" + e.getMessage());
		}
	}

	@Override
	public UserResponseDTO updateUser(UserDTO userDTO, Integer userId) {
		// fetch user based on id,if user not available then throw exception
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(602, "user", "id", userId));
		User userByEmail = this.userRepo.findByEmail(userDTO.getEmail()).orElseThrow(() -> new AlreadyExistException(612,"User", "email",userDTO.getEmail()));
		try {
			user.setName(userDTO.getName());
			user.setEmail(userDTO.getEmail());
			user.setPassword(userDTO.getPassword());
			user.setAbout(userDTO.getAbout());
			User updatedUser = this.userRepo.save(user);
			return this.userToUserResponseDTO(updatedUser);

		} catch (Exception e) {
			throw new ServiceInternalException(611, "error in the user service:updateUser method" + e.getMessage());

		}
	}

	@Override
	public UserResponseDTO getUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(602, "user", "id", userId));
		return this.userToUserResponseDTO(user);
	}

	@Override
	public UserResponse getAllUser(Integer pageNumber, Integer numberOfElements, String sortBy, String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("asc")) ?
		        Sort.by(sortBy).ascending() :
		                Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, numberOfElements,sort);

		Page<User> pageData = this.userRepo.findAll(pageable);
		if (pageData.getTotalElements() == 0) {
			throw new ResourceNotFoundException(604, "there is no user in data base");
		}
		if (pageData.getTotalElements() != 0 && pageData.isEmpty() == true) {
			throw new ResourceNotFoundException(604, "in given page there is no data,Please give correct pagenumber");
		}
		try {
			List<User> content = pageData.getContent();
			// convert user list to userdto list
			List<UserResponseDTO> userDTOList = content.stream().map(user -> this.userToUserResponseDTO(user))
					.collect(Collectors.toList());
			UserResponse userResponse = new UserResponse();
			// set user into userdto class
			userResponse.setUserContent(userDTOList);
			// set current page
			userResponse.setPageNumber(pageData.getNumber());
			// set elements on that page
			userResponse.setNumberOfElements(pageData.getNumberOfElements());
			// set total pages
			userResponse.setTotalpages(pageData.getTotalPages());
			// set total elements
			userResponse.setTotalElements(pageData.getTotalElements());
			// set that is first page or not
			userResponse.setFirstPage(pageData.isFirst());
			// set that is last page or not
			userResponse.setLastPage(pageData.isLast());
			return userResponse;
		} catch (Exception e) {
			throw new ServiceInternalException(611, "error in the user service:createUser method" + e.getMessage());
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
			throw new ServiceInternalException(611, "error in the user service:deleteUser method" + e.getMessage());
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
	public UserResponseDTO userToUserResponseDTO(User user) {

		UserResponseDTO userResponseDTO = this.modelMapper.map(user, UserResponseDTO.class);
//		UserDTO userDTO=new UserDTO();
//		userDTO.setId(user.getId());
//		userDTO.setName(user.getName());
//		userDTO.setEmail(user.getEmail());
//		userDTO.setPassword(user.getPassword());
//		userDTO.setAbout(user.getAbout());

		return userResponseDTO;
	}


	@Override
	public List<UserResponseDTO> findByNameContaining(String keyword) {
		List<User> containingUsers = this.userRepo.findByNameContaining(keyword);
		List<UserResponseDTO> listDtos = containingUsers.stream().map( (user) -> this.modelMapper.map(user,UserResponseDTO.class)
				).collect(Collectors.toList());
		if(listDtos.size()==0) {
			throw new ResourceNotFoundException(612,"there is no user available wiht keyword "+keyword);
		}
		return listDtos;
	}


	@Override
	public UserResponseDTO registerNewUser(UserDTO userDTO) {
		User user=this.modelMapper.map(userDTO,User.class);
		
		//encode the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//roles
		Role role=this.roleRepo.findById(AppConstants.NORMAL_USER).orElseThrow(() -> new ResourceNotFoundException(1111," roles are not available "));
		
		//set role
		user.getRoles().add(role);
		
		User savedUser = this.userRepo.save(user);
		
		
		
		return this.modelMapper.map(savedUser,UserResponseDTO.class);
	}

}
