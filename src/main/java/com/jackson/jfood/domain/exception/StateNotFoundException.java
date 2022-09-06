package com.jackson.jfood.domain.exception;

public class StateNotFoundException extends EntityNotFoundException {
	
	private static final long serialVersionUID = 1L;

	public StateNotFoundException(String message) {
		super(message);
	}
	
	public StateNotFoundException(Long stateId) {
		this(String.format("Estado de código %d não encontrado", stateId));
	}
}
