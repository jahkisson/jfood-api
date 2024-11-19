package com.jackson.jfood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSummaryModel {

	private String code;
	private BigDecimal subtotal;
	private BigDecimal shippingFee;
	private BigDecimal totalValue;
	private String status;
	private OffsetDateTime creationDate;
	private RestaurantSummaryModel restaurant;
	private UserModel customer;
}
