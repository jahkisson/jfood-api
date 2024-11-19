package com.jackson.jfood.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressModel {

	private String zipCode;
	private String address;
	private String district;
	private CitySummaryModel city;
}
