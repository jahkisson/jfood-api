package com.jackson.jfood.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductModel {

	private Long id;
	
	private String name;
	
	private String description;
	
	private BigDecimal price;
	
	private boolean active;
}
