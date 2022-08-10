package com.jackson.jfood.domain.exception;

public class EntityIsBeingUsedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EntityIsBeingUsedException(String message) {
		super(message);
	}

}
