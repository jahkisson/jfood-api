package com.jackson.jfood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jackson.jfood.api.model.CityModel;
import com.jackson.jfood.domain.model.City;

@Component
public class CityModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public CityModel toModel(City city) {
		return modelMapper.map(city, CityModel.class);
	}
	
	public List<CityModel> toCollectionModel(List<City> list) {
		return list.stream().map(x -> toModel(x)).collect(Collectors.toList());
	}
	
}
