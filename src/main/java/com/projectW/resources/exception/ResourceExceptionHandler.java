package com.projectW.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.projectW.Services.exceptions.DataIntegrityViolationException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	//tratamento de erro - obj not found
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandarError> objectNotFound(ObjectNotFoundException e,
			HttpServletRequest request){
		StandarError err = new StandarError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);	 
		
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandarError> dataIntegrity(DataIntegrityViolationException e,
			HttpServletRequest request){
			StandarError err = new StandarError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);	 
			
	}
	
//	org.springframework.web.bind.MethodArgumentNotValidException
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandarError> validException(MethodArgumentNotValidException e,
			HttpServletRequest request){
			ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis());
			for(FieldError fe : e.getBindingResult().getFieldErrors()) {
				err.addError(fe.getField(), fe.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);	 	
			
	}
}
