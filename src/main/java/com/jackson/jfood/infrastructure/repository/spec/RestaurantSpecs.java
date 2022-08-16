package com.jackson.jfood.infrastructure.repository.spec;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.jackson.jfood.domain.model.Restaurant;

public class RestaurantSpecs {

	public static Specification<Restaurant> withFreeShipping() {
		return (root, query, builder) -> builder.equal(root.get("shippingFee"), BigDecimal.ZERO);
	}
	
	public static Specification<Restaurant> withNameLike(String name) {
		return (root, query, builder) -> builder.like(root.get("name"), "%" + name + "%");
	}
}
