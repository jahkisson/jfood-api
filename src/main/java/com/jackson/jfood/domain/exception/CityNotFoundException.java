package com.jackson.jfood.domain.exception;

public class CityNotFoundException extends EntityNotFoundException {
	
	private static final long serialVersionUID = 1L;

	public CityNotFoundException(String message) {
		super(message);
	}
	
	public CityNotFoundException(Long cityId) {
		this(String.format("A cidade de código %d não foi encontrada", cityId));
	}
}
