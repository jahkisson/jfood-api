package com.jackson.jfood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

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
	
	@NotBlank
	@Column(nullable = false)
	private String name;

	@NotNull
	@PositiveOrZero
	@Column(name="shipping_fee", nullable = false)
	private BigDecimal shippingFee;
	
	
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.CuisineId.class)
	@NotNull
	@ManyToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name="cuisine_id", nullable = false)
	private Cuisine cuisine;
	
	@Embedded
	private Address address;
	
	private Boolean active = true;
	
	private Boolean open = true;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime creationTimestamp;
	
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime updateTimestamp;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="restaurant_payment_type", 
				joinColumns = @JoinColumn(name = "restaurant_id"),
				inverseJoinColumns = @JoinColumn(name = "payment_type_id"))
	private Set<PaymentType> paymentTypes = new HashSet<PaymentType>();
	
	@OneToMany(mappedBy = "restaurant")
	private Set<Product> products = new HashSet<Product>();
	
	@ManyToMany
	@JoinTable(name = "restaurant_user_manager",
			joinColumns = @JoinColumn(name = "restaurant_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> managers = new HashSet<>();
	
	public void activate() {
		setActive(true);
	}
	
	public void deactivate() {
		setActive(false);
	}
	
	public void toOpen() {
		setOpen(true);
	}
	
	public void toClose() {
		setOpen(false);
	}
	
	public boolean addManager(User manager) {
		return getManagers().add(manager);
	}
	
	public boolean removeManager(User manager) {
		return getManagers().remove(manager);
	}
	
	public boolean acceptsPaymentType(PaymentType paymentType) {
		return getPaymentTypes().contains(paymentType);
	}
	
	public boolean notAcceptsPaymentType(PaymentType paymentType) {
		return !acceptsPaymentType(paymentType);
	}
}
