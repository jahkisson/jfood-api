package com.jackson.jfood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressInput {

	@NotBlank
	private String zipCode;
	
	@NotBlank
	private String address;
	
	@NotBlank
	private String district;
	
	@Valid
	@NotNull
	private CityIdInput city;
}
