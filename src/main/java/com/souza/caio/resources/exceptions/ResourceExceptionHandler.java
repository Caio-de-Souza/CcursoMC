package com.souza.caio.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.souza.caio.services.exceptions.AuthorizationException;
import com.souza.caio.services.exceptions.DataIntegrityException;
import com.souza.caio.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(
			ObjectNotFoundException exception, HttpServletRequest request){
		StandardError err = new StandardError(
				HttpStatus.NOT_FOUND.value(),
				exception.getMessage(), 
				System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(
			DataIntegrityException exception, HttpServletRequest request){
		StandardError err = new StandardError(
				HttpStatus.BAD_REQUEST.value(),
				exception.getMessage(), 
				System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(
			MethodArgumentNotValidException exception, HttpServletRequest request){
		ValidationError err = new ValidationError(
				HttpStatus.BAD_REQUEST.value(),
				"Erro de validação", 
				System.currentTimeMillis());
		
		for(FieldError error : exception.getBindingResult().getFieldErrors()) {
			err.addError(error.getField(), error.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(
			AuthorizationException exception, HttpServletRequest request){
		StandardError err = new StandardError(
				HttpStatus.FORBIDDEN.value(),
				exception.getMessage(), 
				System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}
}
