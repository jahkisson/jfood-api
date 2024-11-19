package com.jackson.jfood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentTypeIdInput {

	@Valid
	@NotNull
	private Long id;
}
