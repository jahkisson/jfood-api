package com.jackson.jfood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jackson.jfood.api.model.RestaurantModel;
import com.jackson.jfood.domain.model.Restaurant;

@Component
public class RestaurantModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public RestaurantModel toModel(Restaurant restaurant) {
		return modelMapper.map(restaurant, RestaurantModel.class);
	}
	
	public List<RestaurantModel> toCollectionModel(List<Restaurant> list) {
		return list.stream().map(x -> toModel(x)).collect(Collectors.toList());
	}
	
}
