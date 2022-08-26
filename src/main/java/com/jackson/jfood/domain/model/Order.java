package com.jackson.jfood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(name = "order_")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Order {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private BigDecimal subtotal;
	
	@Column(name = "shipping_fee", nullable = false)
	private BigDecimal shippingFee;
	
	@Column(name = "total_value", nullable = false)
	private BigDecimal totalValue;
	
	@CreationTimestamp
	@Column(name = "creation_date", nullable = false)
	private LocalDateTime creationDate;
	
	@Column(name = "confirmation_date")
	private LocalDateTime confirmationDate;
	
	@Column(name = "cancel_date")
	private LocalDateTime cancelDate;
	
	@Column(name = "delivery_date")
	private LocalDateTime deliveryDate;
	
	@Column(nullable = false)
	private OrderStatus status;
	
	@ManyToOne
	@JoinColumn(name="restaurant_id", nullable = false)
	private Restaurant restaurant;
	
	@ManyToOne
	@JoinColumn(name="customer_id", nullable = false)
	private User customer;
	
	@ManyToOne
	@JoinColumn(name="payment_type_id", nullable = false)
	private PaymentType paymentType;
	
	@Embedded
	private Address deliveryAddress;
	
	@OneToMany(mappedBy = "order")
	private List<OrderItem> items;
}