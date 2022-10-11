package com.jackson.jfood.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CuisineIdInput {

	@NotNull
	private Long id;
}
