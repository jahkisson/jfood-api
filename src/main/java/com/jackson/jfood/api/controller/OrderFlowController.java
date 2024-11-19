package com.jackson.jfood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jackson.jfood.domain.service.OrderFlowService;

@RestController
@RequestMapping(value = "/orders/{orderCode}")
public class OrderFlowController {

	@Autowired
	private OrderFlowService orderFlowService;
	
	@PutMapping("/confirm")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirm(@PathVariable String orderCode) {
		orderFlowService.confirm(orderCode);
	}
	
	@PutMapping("/cancel")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancel(@PathVariable String orderCode) {
		orderFlowService.cancel(orderCode);
	}
	
	@PutMapping("/deliver")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deliver(@PathVariable String orderCode) {
		orderFlowService.deliver(orderCode);
	}
	
}
