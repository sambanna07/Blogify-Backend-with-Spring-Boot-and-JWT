package com.demo.blog.payload;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserResponse {
	
	private List<UserDTO> userContent;
	
	private int pageNumber;
	
	private int numberOfElements;
	
	private long totalElements;
	
	private int totalpages;
	
	private boolean lastPage;
	
	private boolean firstPage;

}
