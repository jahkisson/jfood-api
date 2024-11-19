package com.jackson.jfood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jackson.jfood.api.model.OrderModel;
import com.jackson.jfood.domain.model.Order;

@Component
public class OrderModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public OrderModel toModel(Order order) {
		return modelMapper.map(order, OrderModel.class);
	}
	
	public List<OrderModel> toCollectionModel(List<Order> orders) {
		return orders.stream().map(x -> toModel(x)).collect(Collectors.toList());
	}
}
