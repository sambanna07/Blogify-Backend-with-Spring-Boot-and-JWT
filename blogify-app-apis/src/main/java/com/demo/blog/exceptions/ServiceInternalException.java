package com.demo.blog.exceptions;

import lombok.Getter;

@Getter
public class ServiceInternalException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public int errorCode;
	public String message;
	
	
	public ServiceInternalException(int errorCode, String message) {
		super(String.format(message));
		this.errorCode = errorCode;
		this.message = message;
	}
	

}
