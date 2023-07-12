package com.demo.blog.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.demo.blog.exceptions.AlreadyExistException;
import com.demo.blog.exceptions.ResourceNotFoundException;

import lombok.extern.java.Log;

@ControllerAdvice
@Log
public class UserControllerAdvice extends ResponseEntityExceptionHandler{
	
	/**
	 * handle the ResourceNotFoundException and return user friendly message
	 * @param resourceNotFoundException
	 * @return
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException){
		log.info(resourceNotFoundException.getMessage());
		return new ResponseEntity<>(resourceNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
		
	}
    
	/**
	 * handle AlreadyExistException and return user friendly message
	 * @param alreadyExistException
	 * @return
	 */
	@ExceptionHandler(AlreadyExistException.class)
	public ResponseEntity<?> handleAlreadyExistException(AlreadyExistException alreadyExistException){
		return new ResponseEntity<>(alreadyExistException.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	
	/**
	 * Handles HttpRequestMethodNotSupportedException and returns an appropriate
	 * response entity with error message and HTTP status code.
	 * 
	 * @param ex      the HttpRequestMethodNotSupportedException to handle
	 * @param headers the HttpHeaders of the request
	 * @param request the WebRequest
	 * @return ResponseEntity<Object> containing the error message and HTTP status
	 *         code
	 */
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<>("You are using the wrong method for sending the request. Please select the correct method.",
				HttpStatus.BAD_REQUEST);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(errorMessage);
	}

	
	
	

}
