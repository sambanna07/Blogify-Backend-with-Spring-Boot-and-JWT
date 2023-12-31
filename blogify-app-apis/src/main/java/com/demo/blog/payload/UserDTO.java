package com.demo.blog.payload;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
	
	private int id;	
	
	@NotBlank(message = "name field required")
	@Length(max =30,message="you can not give more than 30 characters in name field")
	@Pattern(regexp = "^[a-zA-Z ]+$",message ="in name field only characters allowed")
	private String name;
	
	@NotBlank(message="email field required")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",message ="please enter valid email")
	private String email;
	

	@NotBlank(message="password field required")
	@Size(min  = 8 ,message = "minimum length of password must be 8")
	private String password;
	
	@NotBlank(message="about field required")
	@Length(min = 15,message = "minimum 15 letters required for about filed")
	private String about;
	
	private Set<CommentsDTO> comments=new HashSet<>();
	

}
