package com.jackson.jfood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jackson.jfood.api.model.input.CuisineInput;
import com.jackson.jfood.domain.model.Cuisine;

@Component
public class CuisineInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Cuisine toDomainObject(CuisineInput cuisineInput) {
		return modelMapper.map(cuisineInput, Cuisine.class);
	}
	
	public void copyToDomainObject(CuisineInput cuisineInput, Cuisine cuisine) {
		modelMapper.map(cuisineInput, cuisine);
	}
}
