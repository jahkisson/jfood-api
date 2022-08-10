package com.jackson.jfood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jackson.jfood.domain.exception.EntityNotFoundException;
import com.jackson.jfood.domain.exception.ReferencedEntityNotFoundException;
import com.jackson.jfood.domain.model.Cuisine;
import com.jackson.jfood.domain.model.Restaurant;
import com.jackson.jfood.domain.repository.CuisineRepository;
import com.jackson.jfood.domain.repository.RestaurantRepository;

@Service
public class RestaurantRegistrationService {

	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private CuisineRepository cuisineRepository;
	
	public Restaurant save(Restaurant restaurant) {
		restaurant = copyUpdatePropertiesIfNeeded(restaurant);
		
		Long cuisineId = restaurant.getCuisine().getId();
		Cuisine cuisine = cuisineRepository.find(cuisineId);
		if (cuisine == null)
			throw new ReferencedEntityNotFoundException(String.format("Não existe cadastro de cozinha com o código %d", cuisineId));
		
		restaurant.setCuisine(cuisine);
		return restaurantRepository.persist(restaurant);
	}
	
	private Restaurant copyUpdatePropertiesIfNeeded(Restaurant restaurant) {
		if (restaurant.getId() == null || restaurant.getId() <= 0)
			return restaurant;
		
		Restaurant restaurantToPersist = restaurantRepository.find(restaurant.getId());
		if (restaurantToPersist == null)
			throw new EntityNotFoundException(String.format("O restaurante com o código %d não foi encontrado", restaurant.getId()));
		BeanUtils.copyProperties(restaurant, restaurantToPersist, "id");
		return restaurantToPersist;
	}
}
