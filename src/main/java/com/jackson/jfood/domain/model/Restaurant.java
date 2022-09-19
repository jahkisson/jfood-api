package com.jackson.jfood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jackson.jfood.core.validation.Groups;
import com.jackson.jfood.core.validation.ValueZeroIncludesDescription;

import lombok.Data;
import lombok.EqualsAndHashCode;

@ValueZeroIncludesDescription(fieldValue = "shippingFee", 
	fieldDescription = "name", mandatoryDescription = "Frete Grátis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurant {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@NotNull
	//@NotEmpty
	@NotBlank
	@Column(nullable = false)
	private String name;

	@NotNull
	//@DecimalMin("0")
	@PositiveOrZero
	//@ShippingFee
	//@Multiple(number = 5)
	@Column(name="shipping_fee", nullable = false)
	private BigDecimal shippingFee;
	
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.CuisineId.class)
	@NotNull
	//@JsonIgnore
	//@JsonIgnoreProperties("hibernateLazyInitializer")
	@ManyToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name="cuisine_id", nullable = false)
	private Cuisine cuisine;
	
	@JsonIgnore
	@Embedded
	private Address address;
	
	@JsonIgnore
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime creationTimestamp;
	
	@JsonIgnore
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime updateTimestamp;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="restaurant_payment_type", 
				joinColumns = @JoinColumn(name = "restaurant_id"),
				inverseJoinColumns = @JoinColumn(name = "payment_type_id"))
	private List<PaymentType> paymentTypes = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "restaurant")
	private List<Product> products = new ArrayList<>();
}
