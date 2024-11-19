package com.jackson.jfood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.jackson.jfood.domain.exception.BusinessException;

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
	
	private String code;
	
	@Column(nullable = false)
	private BigDecimal subtotal;
	
	@Column(name = "shipping_fee", nullable = false)
	private BigDecimal shippingFee;
	
	@Column(name = "total_value", nullable = false)
	private BigDecimal totalValue;
	
	@CreationTimestamp
	@Column(name = "creation_date", nullable = false)
	private OffsetDateTime creationDate;
	
	@Column(name = "confirmation_date")
	private OffsetDateTime confirmationDate;
	
	@Column(name = "cancel_date")
	private OffsetDateTime cancelDate;
	
	@Column(name = "delivery_date")
	private OffsetDateTime deliveryDate;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus status = OrderStatus.Created;
	
	@ManyToOne
	@JoinColumn(name="restaurant_id", nullable = false)
	private Restaurant restaurant;
	
	@ManyToOne
	@JoinColumn(name="customer_id", nullable = false)
	private User customer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="payment_type_id", nullable = false)
	private PaymentType paymentType;
	
	@Embedded
	private Address deliveryAddress;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> items;
	
	public void calculateTotalValue() {
		getItems().forEach(OrderItem::calculateTotalPrice);
		this.subtotal = getItems().stream().map(x -> x.getTotalPrice())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		this.totalValue = this.subtotal.add(this.shippingFee);
	}
	
	public void defineShippingFee() {
		setShippingFee(getRestaurant().getShippingFee());
	}
	
	public void assignOrderToItems() {
		getItems().forEach(x -> x.setOrder(this));
	}
	
	public void confirm() {
		setStatus(OrderStatus.Confirmed);
		setConfirmationDate(OffsetDateTime.now());
	}
	
	public void deliver() {
		setStatus(OrderStatus.Delivered);
		setDeliveryDate(OffsetDateTime.now());
	}
	
	public void cancel() {
		setStatus(OrderStatus.Cancelled);
		setCancelDate(OffsetDateTime.now());
	}
	
	private void setStatus(OrderStatus newStatus) {
		if (getStatus().equals(newStatus))
			throw new BusinessException("Order is already on status " + newStatus.getDescription());
		if (getStatus().cantChangeTo(newStatus))
			throw new BusinessException(String.format("Status of order %s can't be changed from %s to %s", 
					getCode(), getStatus().getDescription(), newStatus.getDescription()));
		this.status = newStatus;
	}
	
	@PrePersist
	private void generateCode() {
		setCode(UUID.randomUUID().toString());
	}
}