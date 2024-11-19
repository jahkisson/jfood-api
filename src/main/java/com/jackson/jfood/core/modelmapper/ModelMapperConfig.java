package com.jackson.jfood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jackson.jfood.api.model.AddressModel;
import com.jackson.jfood.api.model.input.OrderItemInput;
import com.jackson.jfood.domain.model.Address;
import com.jackson.jfood.domain.model.OrderItem;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		
		modelMapper.createTypeMap(OrderItemInput.class, OrderItem.class)
			.addMappings(mapper -> mapper.skip(OrderItem::setId));
		
		var addressToAdressModelTypeMap = modelMapper.createTypeMap(Address.class, AddressModel.class);
		addressToAdressModelTypeMap.<String>addMapping(
				src -> src.getCity().getState().getName(), 
				(dest, value) -> dest.getCity().setState(value));
		
		return modelMapper;
	}
}
