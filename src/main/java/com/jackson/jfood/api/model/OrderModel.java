package com.jackson.jfood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import com.jackson.jfood.api.OrderItemModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderModel {

	private String code;;
	private BigDecimal subtotal;
	private BigDecimal shippingFee;
	private BigDecimal totalValue;
	private String status;
	private OffsetDateTime creationDate;
	private OffsetDateTime confirmationDate;
	private OffsetDateTime deliveryDate;
	private OffsetDateTime cancelDate;
	private RestaurantSummaryModel restaurant;
	private UserModel customer;
	private PaymentTypeModel paymentType;
	private AddressModel deliveryAddress;
	private List<OrderItemModel> items;
}
