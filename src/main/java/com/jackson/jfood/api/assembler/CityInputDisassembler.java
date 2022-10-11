package com.jackson.jfood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jackson.jfood.api.model.input.CityInput;
import com.jackson.jfood.domain.model.City;
import com.jackson.jfood.domain.model.State;

@Component
public class CityInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public City toDomainObject(CityInput cityInput) {
		return modelMapper.map(cityInput, City.class);
	}
	
	public void copyToDomainObject(CityInput cityInput, City city) {
		avoidHibernateExceptionIdChange(city);
		modelMapper.map(cityInput, city);
	}

	private void avoidHibernateExceptionIdChange(City city) {
		city.setState(new State());
	}
}
