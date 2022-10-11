package com.jackson.jfood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jackson.jfood.api.model.input.RestaurantInput;
import com.jackson.jfood.domain.model.Cuisine;
import com.jackson.jfood.domain.model.Restaurant;

@Component
public class RestaurantInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Restaurant toDomainObject(RestaurantInput restaurantInput) {
		return modelMapper.map(restaurantInput, Restaurant.class);
	}
	
	public void copyToDomainObject(RestaurantInput restaurantInput, Restaurant restaurant) {
		avoidHibernateExceptionIdChange(restaurant);
		modelMapper.map(restaurantInput, restaurant);
	}

	private void avoidHibernateExceptionIdChange(Restaurant restaurant) {
		restaurant.setCuisine(new Cuisine());
	}
}
