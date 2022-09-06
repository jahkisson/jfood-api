package com.jackson.jfood.domain.exception;

public class EntityIsBeingUsedException extends BusinessException {

	private static final long serialVersionUID = 1L;
	
	public EntityIsBeingUsedException(String message) {
		super(message);
	}

}
