package com.edgardjr.cursosb.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends DefaultError {

	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errors = new ArrayList<>();
	
	public ValidationError(Integer status, String msg, Long timestamp) {
		super(status, msg, timestamp);
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public boolean addError(String field, String message) {
		return this.errors.add(new FieldMessage(field, message));
	}
}
