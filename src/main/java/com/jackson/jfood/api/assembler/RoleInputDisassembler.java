package com.jackson.jfood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jackson.jfood.api.model.input.RoleInput;
import com.jackson.jfood.domain.model.Role;

@Component
public class RoleInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Role toDomainObject(RoleInput roleInput) {
		return modelMapper.map(roleInput, Role.class);
	}
	
	public void copyToDomainObject(RoleInput roleInput, Role role) {
		modelMapper.map(roleInput, role);
	}
}
