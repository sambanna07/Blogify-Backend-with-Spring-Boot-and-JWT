package com.demo.blog.exceptions;

import lombok.Getter;

/**
 * for handling ResourceNotFoundException
 * @author samundar singh rathore
 *
 */

@Getter
public class ResourceNotFoundException extends RuntimeException{
	
	
	private static final long serialVersionUID = 1L;
	
	int errorCode;
	String resourceName;
	String fieldName;
	long fieldValue;
	
	/**
	 * for giving our custom message
	 * @param resourceName
	 * @param fieldName
	 * @param fieldValue
	 */
	public ResourceNotFoundException(Integer errorCode, String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : %d", resourceName,fieldName,fieldValue));
		this.errorCode=errorCode;
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	} 
	
	/**
	 * When database is empty
	 * @param errorCode
	 * @param resourceName
	 */
	public ResourceNotFoundException(Integer errorCode, String resourceName) {
		super(String.format("%S", resourceName));
		this.errorCode=errorCode;
		this.resourceName = resourceName;
	} 
	
	

}
