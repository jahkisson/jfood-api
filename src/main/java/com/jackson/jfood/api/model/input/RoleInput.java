package com.jackson.jfood.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleInput {

	@NotBlank
	private String name;
}
