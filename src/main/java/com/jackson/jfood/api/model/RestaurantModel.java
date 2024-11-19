package com.jackson.jfood.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantModel {

	private Long id;
	private String name;
	private BigDecimal shippingFee;
	private CuisineModel cuisine;
	private Boolean active;
	private Boolean open;
	private AddressModel address;
}
