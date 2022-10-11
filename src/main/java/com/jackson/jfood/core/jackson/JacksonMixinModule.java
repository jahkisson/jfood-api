package com.jackson.jfood.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jackson.jfood.api.model.mixin.CityMixin;
import com.jackson.jfood.api.model.mixin.CuisineMixin;
import com.jackson.jfood.domain.model.City;
import com.jackson.jfood.domain.model.Cuisine;

@Component
public class JacksonMixinModule extends SimpleModule {

	private static final long serialVersionUID = 1L;

	public JacksonMixinModule() {
		setMixInAnnotation(City.class, CityMixin.class);
		setMixInAnnotation(Cuisine.class, CuisineMixin.class);
	}
}
