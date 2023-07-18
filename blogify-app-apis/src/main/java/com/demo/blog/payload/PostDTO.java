package com.demo.blog.payload;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * PostDTO class for intermediate operation and send data to client
 * @author Samundar Singh Rathore
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
	
	

	private Integer postId;
	

	@NotBlank(message = "post title must required")
	@Size(min =3,message = "minimum 3 characters required")
	@Size(max = 100,message = "more than 500 characters not allowed")
	private String postTitle;
	

	@NotBlank(message = "post content must required")
	@Size(min =10,message = "minimum 10 characters required")
	@Size(max = 5000,message = "more than 5000 characters not allowed")
	private String postContent;
	


	private String postImage;
	

	private Date postCreationDate;
	

	private CategoryDTO category;
	

	private UserDTO user;

}
