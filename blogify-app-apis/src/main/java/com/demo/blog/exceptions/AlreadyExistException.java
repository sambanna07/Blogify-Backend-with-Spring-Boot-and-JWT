package com.demo.blog.exceptions;

import lombok.Getter;

/**
 * if resource already present with that id
 * @author Samundar singh rathore
 *
 */
@Getter
public class AlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public int errorCode;
	public String resourceName;
	public String fieldName;
	public String resourceValue;
	
	public AlreadyExistException(Integer errorCode, String resourceName, String fieldName, String resourceValue) {
		super(String.format("%s is already available in data-base with %s : %s", resourceName,fieldName, resourceValue));
		this.errorCode=errorCode;
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.resourceValue = resourceValue;
	}

	

}
