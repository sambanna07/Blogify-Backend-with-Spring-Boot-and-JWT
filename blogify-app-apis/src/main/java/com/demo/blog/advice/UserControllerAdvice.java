package com.demo.blog.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.demo.blog.exceptions.AlreadyExistException;
import com.demo.blog.exceptions.ResourceNotFoundException;
import com.demo.blog.exceptions.ServiceInternalException;

@RestControllerAdvice
public class UserControllerAdvice extends ResponseEntityExceptionHandler {



	/**
	 * handle the ResourceNotFoundException and return user friendly message
	 * 
	 * @param resourceNotFoundException
	 * @return
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
		return ResponseEntity.status(resourceNotFoundException.getErrorCode())
				.body(resourceNotFoundException.getMessage());

	}

	/**
	 * handle AlreadyExistException and return user friendly message
	 * 
	 * @param alreadyExistException
	 * @return
	 */
	@ExceptionHandler(AlreadyExistException.class)
	public ResponseEntity<Object> handleAlreadyExistException(AlreadyExistException alreadyExistException) {
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
		return new ResponseEntity<>(
				"You are using the wrong method for sending the request. Please select the correct method.",
				HttpStatus.BAD_REQUEST);
	}

	/**
	 * handle if user give not valid data
	 */
	@Override
//	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> resp = new HashMap();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			resp.put(fieldName, message);
		});
		return ResponseEntity.status(699).body(resp);

	}

	@ExceptionHandler(ServiceInternalException.class)
	public ResponseEntity<Object> handleException(ServiceInternalException serviceInternalException) {
		return ResponseEntity.status(serviceInternalException.getErrorCode())
				.body(serviceInternalException.getMessage());

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception exception) {
		return new ResponseEntity<>("Something went wrong due : " + exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<String> handleNumberFormatException(NumberFormatException numberFormatException) {
		return new ResponseEntity<String>(
				"in url you are giving wrong format data" + numberFormatException.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
    /**
     * Handles MethodArgumentTypeMismatchException and returns an appropriate
     * response entity with error message and HTTP status code.
     *
     * @param ex      the MethodArgumentTypeMismatchException to handle
     * @param headers the HttpHeaders of the request
     * @param request the WebRequest
     * @return ResponseEntity<Object> containing the error message and HTTP status code
     */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<String> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
	                                                               HttpHeaders headers,
	                                                               HttpStatus status,
	                                                               WebRequest request) {
	    String errorMessage = "Failed to convert value of type '" +
	            ex.getValue().toString() +
	            "' to required type '" +
	            ex.getRequiredType().getSimpleName() +
	            "'";
	    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}


}
