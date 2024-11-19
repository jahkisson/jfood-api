package com.jackson.jfood.api;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemModel {

	private Long productId;
	private String productName;
	private Integer quantity;
	private BigDecimal unitPrice;
	private BigDecimal totalPrice;
	private String additionalInfo;
}
