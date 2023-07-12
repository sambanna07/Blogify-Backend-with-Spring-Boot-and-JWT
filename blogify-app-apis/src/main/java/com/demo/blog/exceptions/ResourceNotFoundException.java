package com.demo.blog.exceptions;
/**
 * for handling ResourceNotFoundException
 * @author samundar singh rathore
 *
 */
public class ResourceNotFoundException extends RuntimeException{
	
	
	private static final long serialVersionUID = 1L;
	String resourceName;
	String fieldName;
	long fieldValue;
	
	/**
	 * for giving our custom message
	 * @param resourceName
	 * @param fieldName
	 * @param fieldValue
	 */
	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : %l", resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	} 
	
	

}
