package com.jackson.jfood.api.model.mixin;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jackson.jfood.domain.model.Restaurant;

public abstract class CuisineMixin {

	@JsonIgnore
	private List<Restaurant> restaurants;
}
