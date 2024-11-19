package com.jackson.jfood.domain.exception;

public class OrderNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;
	
	public OrderNotFoundException(String code) {
		super(String.format("Não existe pedido com o código %s", code));
	}

}
