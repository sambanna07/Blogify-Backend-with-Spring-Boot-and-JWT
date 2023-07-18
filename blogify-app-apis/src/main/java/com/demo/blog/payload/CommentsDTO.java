package com.demo.blog.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CommentsDTO {
	
	int id;
	
	
	@NotBlank(message = "content field is required")
	@Size(min = 10,message = "minimum 10 characters are required")
	String content;
	

}
