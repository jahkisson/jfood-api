package com.jackson.jfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jackson.jfood.api.assembler.OrderInputDisassembler;
import com.jackson.jfood.api.assembler.OrderModelAssembler;
import com.jackson.jfood.api.assembler.OrderSummaryModelAssembler;
import com.jackson.jfood.api.model.OrderModel;
import com.jackson.jfood.api.model.OrderSummaryModel;
import com.jackson.jfood.api.model.input.OrderInput;
import com.jackson.jfood.domain.exception.BusinessException;
import com.jackson.jfood.domain.exception.EntityNotFoundException;
import com.jackson.jfood.domain.model.Order;
import com.jackson.jfood.domain.model.User;
import com.jackson.jfood.domain.repository.OrderRepository;
import com.jackson.jfood.domain.service.OrderRequestService;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderRequestService orderRequest;
	
	@Autowired
	private OrderModelAssembler orderModelAssembler;
	
	@Autowired
	private OrderSummaryModelAssembler orderSummaryModelAssembler;
	
	@Autowired
	private OrderInputDisassembler orderInputDisassembler;
	
	@GetMapping
	private List<OrderSummaryModel> list() {
		List<Order> allOrders = orderRepository.findAll();
		return orderSummaryModelAssembler.toCollectionModel(allOrders);
	}
	
	@GetMapping("/{orderCode}")
	public OrderModel find(@PathVariable String orderCode) {
		Order order = orderRequest.findOrFail(orderCode);
		return orderModelAssembler.toModel(order);
	}
	
	@PostMapping
	private OrderModel add(@RequestBody OrderInput orderInput) {
		try {
			Order order = orderInputDisassembler.toDomainObject(orderInput);
			
			// TODO get authenticated user
			order.setCustomer(new User());
			order.getCustomer().setId(1L);
			
			order = orderRequest.save(order);
			return orderModelAssembler.toModel(order);
		} catch (EntityNotFoundException ex) {
			throw new BusinessException(ex.getMessage(), ex);
		}
	}
}
