package com.jackson.jfood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jackson.jfood.api.model.input.OrderInput;
import com.jackson.jfood.domain.model.Order;

@Component
public class OrderInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Order toDomainObject(OrderInput orderInput) {
		return modelMapper.map(orderInput, Order.class);
	}
}
