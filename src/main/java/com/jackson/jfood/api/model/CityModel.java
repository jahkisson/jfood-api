package com.jackson.jfood.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityModel {

	private Long id;
	private String name;
	private StateModel state;
}
