package com.jackson.jfood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemInput {

	@Valid
	@NotNull
	@Positive
	private int quantity;
	
	@Valid
	@NotNull
	@Positive
	private Long productId;
	
	private String additionalInfo;
}
