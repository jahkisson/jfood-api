package com.jackson.jfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jackson.jfood.domain.exception.RestaurantNotFoundException;
import com.jackson.jfood.domain.model.Cuisine;
import com.jackson.jfood.domain.model.Restaurant;
import com.jackson.jfood.domain.repository.RestaurantRepository;

@Service
public class RestaurantRegistrationService {

	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private CuisineRegistrationService cuisineRegistration;
	
	public Restaurant findByIdOrFail(Long restaurantId) {
		return restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
	}
	
	public Restaurant save(Restaurant restaurant) {
		Long cuisineId = restaurant.getCuisine().getId();
		Cuisine cuisine = cuisineRegistration.findByIdOrFail(cuisineId);
		
		restaurant.setCuisine(cuisine);
		return restaurantRepository.save(restaurant);
	}
}
