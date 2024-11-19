package com.jackson.jfood.domain.exception;

public class UserNotFoundException extends EntityNotFoundException {
	
	private static final long serialVersionUID = 1L;
	
	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(Long userId) {
		this(String.format("O usuário de código %d não foi encontrado", userId));
	}
}
