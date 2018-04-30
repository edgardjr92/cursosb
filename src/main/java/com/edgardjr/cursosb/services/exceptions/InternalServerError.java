package com.edgardjr.cursosb.services.exceptions;

public class InternalServerError extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InternalServerError(String msg) {
		super(msg);
	}
	
	public InternalServerError(String msg, Throwable cause) {
		super(msg, cause);
	}

}
