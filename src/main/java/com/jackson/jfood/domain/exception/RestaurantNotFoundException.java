package com.jackson.jfood.domain.exception;

public class RestaurantNotFoundException extends EntityNotFoundException {
	
	private static final long serialVersionUID = 1L;

	public RestaurantNotFoundException(String message) {
		super(message);
	}
	
	public RestaurantNotFoundException(Long restaurantId) {
		this(String.format("O restaurante com o código %d não foi encontrado", restaurantId));
	}
}
