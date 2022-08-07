package com.jackson.jfood.domain.repository;

import java.util.List;

import com.jackson.jfood.domain.model.Restaurant;

public interface RestaurantRepository {

	List<Restaurant> list();
	Restaurant find(Long id);
	Restaurant persist(Restaurant restaurant);
	void remove(Restaurant restaurant);
}
