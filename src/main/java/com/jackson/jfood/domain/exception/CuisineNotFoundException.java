package com.jackson.jfood.domain.exception;

public class CuisineNotFoundException extends EntityNotFoundException {
	
	private static final long serialVersionUID = 1L;

	public CuisineNotFoundException(String message) {
		super(message);
	}
	
	public CuisineNotFoundException(Long cuisineId) {
		this(String.format("Cozinha de código %d não encontrada", cuisineId));
	}
}
