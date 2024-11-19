package com.jackson.jfood.domain.model;

import java.util.Arrays;
import java.util.List;

public enum OrderStatus {
	Created("Created"), 
	Confirmed("Confirmed", Created), 
	Delivered("Delivered", Confirmed), 
	Cancelled("Cancelled", Created);
	
	private String description;
	private List<OrderStatus> previousStatus; 
	
	OrderStatus(String description, OrderStatus...orderStatus) {
		this.description = description;
		this.previousStatus = Arrays.asList(orderStatus);
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public boolean cantChangeTo(OrderStatus newStatus) {
		return !newStatus.previousStatus.contains(this);
	}
}
