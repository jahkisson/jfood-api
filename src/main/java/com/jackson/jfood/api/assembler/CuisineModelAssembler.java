package com.jackson.jfood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jackson.jfood.api.model.CuisineModel;
import com.jackson.jfood.domain.model.Cuisine;

@Component
public class CuisineModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public CuisineModel toModel(Cuisine cuisine) {
		return modelMapper.map(cuisine, CuisineModel.class);
	}
	
	public List<CuisineModel> toCollectionModel(List<Cuisine> list) {
		return list.stream().map(x -> toModel(x)).collect(Collectors.toList());
	}
	
}
