package com.demo.blog.payload;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.UniqueElements;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
	
	
	
	private Integer categoryId;
	
	@NotEmpty(message = "title must be required")
	@Size(min = 5,message = "title length must be minimum 5 characters")
	private String categoryTitle;
	
	
	@NotEmpty(message = "description must be required")
	@Size(min = 10,message = "length of description must be minimum 10")
	private String categoryDescription;

}
