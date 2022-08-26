package com.jackson.jfood.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(name = "order_item")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class OrderItem {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private BigDecimal quantity;
	
	@Column(name = "unit_price", nullable = false)
	private BigDecimal unitPrice;
	
	@Column(name = "total_price", nullable = false)
	private BigDecimal totalPrice;
	
	@Column(name = "additional_info")
	private String additionalInfo;
	
	@ManyToOne
	@JoinColumn(name="product_id", nullable = false)
	private Product product;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="order_id", nullable = false)
	private Order order;
}