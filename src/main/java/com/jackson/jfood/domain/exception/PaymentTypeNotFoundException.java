package com.jackson.jfood.domain.exception;

public class PaymentTypeNotFoundException extends EntityNotFoundException {
	
	private static final long serialVersionUID = 1L;

	public PaymentTypeNotFoundException(String message) {
		super(message);
	}
	
	public PaymentTypeNotFoundException(Long paymentTypeId) {
		this(String.format("Forma de pagamento de código %d não encontrado", paymentTypeId));
	}
}
