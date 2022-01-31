package com.projectW.Services.exceptions;

public class ObjectNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ObjectNotFoundException(String msg) {
		super(msg);
	}
	
	//Throwable - cause real
	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
