package com.jackson.jfood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jackson.jfood.domain.model.State;

public abstract class CityMixin {

	@JsonIgnoreProperties(value = "name", allowGetters = true)
	private State state;
}
