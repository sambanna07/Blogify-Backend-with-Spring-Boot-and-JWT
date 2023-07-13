package com.demo.blog.advice;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.demo.blog.exceptions.AlreadyExistException;
import com.demo.blog.exceptions.ResourceNotFoundException;
import com.demo.blog.exceptions.ServiceInternalException;

import lombok.extern.java.Log;

@RestControllerAdvice
@Log
public class UserControllerAdvice extends ResponseEntityExceptionHandler{
	
	private static final String RESOURCE_NOT_FOUND = null;


	/**
	 * handle the ResourceNotFoundException and return user friendly message
	 * @param resourceNotFoundException
	 * @return
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException
	(ResourceNotFoundException resourceNotFoundException){
		return ResponseEntity
				.status(resourceNotFoundException.getErrorCode())
				.body(resourceNotFoundException.getMessage());
		
	}
    
	/**
	 * handle AlreadyExistException and return user friendly message
	 * @param alreadyExistException
	 * @return
	 */
	@ExceptionHandler(AlreadyExistException.class)
	public ResponseEntity<?> handleAlreadyExistException(AlreadyExistException alreadyExistException){
		return ResponseEntity.status(alreadyExistException.getErrorCode()).body(alreadyExistException.getMessage());
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
	
	/**
	 * handle if user give not valid data
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
//		String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
//        return ResponseEntity.badRequest().body(errorMessage);
		Map<String,String> resp=new HashMap();
		ex.getBindingResult().getAllErrors()
		        .forEach((error) ->
		        {
		        	String fieldName=((FieldError)error).getField();
		        	String message=error.getDefaultMessage();
		        	resp.put(fieldName, message);
		        });
		return ResponseEntity.status(699).body(resp);

	}
	
	
	@ExceptionHandler(ServiceInternalException.class)
	public ResponseEntity<?> handleException(ServiceInternalException serviceInternalException) {
		return ResponseEntity.status(serviceInternalException.getErrorCode()).
				body(serviceInternalException.getMessage());
		
	}
	

	
	
	

}
