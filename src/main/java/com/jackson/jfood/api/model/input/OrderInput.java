package com.jackson.jfood.api.model.input;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderInput {

	@Valid
	@NotNull
	private RestaurantIdInput restaurant;
	
	@Valid
	@NotNull
	private PaymentTypeIdInput paymentType;
	
	@Valid
	@NotNull
	private AddressInput deliveryAddress;
	
	@Valid
	@NotNull
	@Size(min = 1)
	public List<OrderItemInput> items;
}
