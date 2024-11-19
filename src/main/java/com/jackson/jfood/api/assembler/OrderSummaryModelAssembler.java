package com.jackson.jfood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jackson.jfood.api.model.OrderSummaryModel;
import com.jackson.jfood.domain.model.Order;

@Component
public class OrderSummaryModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public OrderSummaryModel toModel(Order order) {
		return modelMapper.map(order, OrderSummaryModel.class);
	}
	
	public List<OrderSummaryModel> toCollectionModel(List<Order> orders) {
		return orders.stream().map(x -> toModel(x)).collect(Collectors.toList());
	}
}
