package com.jackson.jfood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.jackson.jfood.domain.model.Restaurant;

public interface RestaurantRepositoryQueries {

	List<Restaurant> find(String name, BigDecimal initialShippingFee, BigDecimal finalShippingFee);
	
	List<Restaurant> findWithFreeShipping(String name);

}