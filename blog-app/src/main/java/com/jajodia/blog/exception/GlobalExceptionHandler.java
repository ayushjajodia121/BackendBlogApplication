package com.jajodia.blog.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jajodia.blog.payload.ApiResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(UserTokenLoggedOutExpiredException.class)
	public ResponseEntity<ApiResponse> UserTokenLoggedOutExpiredExceptionHandler(UserTokenLoggedOutExpiredException ex) {
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message,false, LocalDateTime.now()+"");
		return new ResponseEntity<>(apiResponse,HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> 	handleResourceNotFoundExceptionHandler(ResourceNotFoundException ex)
	{
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message,false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ApiResponse> 	handleInvalidCredentialsException(InvalidCredentialsException ex)
	{
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message,false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(PasswordMismatchException.class)
	public ResponseEntity<ApiResponse> 	passwordMismatchExceptionHandler(PasswordMismatchException ex)
	{
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message,false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_ACCEPTABLE);
	}
	
	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<Map<String,String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex)
//	{
//		Map<String, String> resp = new HashMap<>();
//		ex.getBindingResult().getAllErrors().forEach((error)->{
//			String fieldName = ((FieldError)error).getField();
//			String message = error.getDefaultMessage();
//			resp.put(fieldName, message);
//		});
//		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
//	}
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<ApiResponse> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex)
	{
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message+": Title Already Exists. ",false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_ACCEPTABLE);	
	}
	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<ApiResponse> 	categoryNotFoundExceptionHandler(CategoryNotFoundException ex)
	{
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message,false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}
//	@ExceptionHandler(ServletException.class)
//	public ResponseEntity<ApiResponse> 	servletExceptionHandler(ServletException ex)
//	{
//		String message = ex.getMessage();
//		ApiResponse apiResponse = new ApiResponse(message,false);
//		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
//	}

}
