package com.jackson.jfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jackson.jfood.domain.model.Order;

@Service
public class OrderFlowService {

	@Autowired
	private OrderRequestService orderRequestService;
	
	@Transactional
	public void confirm(String orderCode) {
		Order order = orderRequestService.findOrFail(orderCode);
		order.confirm();
	}
	
	@Transactional
	public void cancel(String orderCode) {
		Order order = orderRequestService.findOrFail(orderCode);
		order.cancel();
	}
	
	@Transactional
	public void deliver(String orderCode) {
		Order order = orderRequestService.findOrFail(orderCode);
		order.deliver();
	}
}
