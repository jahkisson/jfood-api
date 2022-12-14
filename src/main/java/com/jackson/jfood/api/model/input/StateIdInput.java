package com.jackson.jfood.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateIdInput {

	@NotNull
	private Long id;
}
