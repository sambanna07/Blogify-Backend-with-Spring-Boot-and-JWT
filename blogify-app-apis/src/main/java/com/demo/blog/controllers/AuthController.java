package com.demo.blog.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.blog.exceptions.ResourceNotFoundException;
import com.demo.blog.payload.JwtAuthRequest;
import com.demo.blog.payload.JwtAuthResponse;
import com.demo.blog.payload.UserDTO;
import com.demo.blog.payload.UserResponseDTO;
import com.demo.blog.security.JwtTokenHelper;
import com.demo.blog.services.UserService;

import lombok.extern.log4j.Log4j;


@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) {
		System.out.println("enter in create token");
		System.out.println(request);
		
	    try {
	        // authenticate based on username and password
	        this.authenticate(request.getUsername(), request.getPassword());

	        // load user details
	        UserDetails user = this.userDetailsService.loadUserByUsername(request.getUsername());

	        // generate token
	        String token = this.jwtTokenHelper.generateToken(user);

	        JwtAuthResponse response = new JwtAuthResponse();
	        response.setToken(token);
	        response.setUser(this.mapper.map(user,UserDTO.class));
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (DisabledException e) {
	        throw new ResourceNotFoundException(1111, "user is disabled with username: " + request.getUsername());
	    }
	}


	// authenticate bASED ON USER NAME AND PASSWORD
	private void authenticate(String username, String password) {
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken 
		= new UsernamePasswordAuthenticationToken(username, password);
		try {
		this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		}catch(DisabledException e) {
			throw new ResourceNotFoundException(1111,"user is disable with username :"+username);		
		}
	}
	
	/**
	 * End point for register new user
	 * @param userDTO
	 * @return saved user object
	 */
	@PostMapping("/register")
	public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserDTO userDTO){
		UserResponseDTO newUser = this.userService.registerNewUser(userDTO);
		
		return new ResponseEntity<UserResponseDTO>(newUser,HttpStatus.CREATED);
	}

}
