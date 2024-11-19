package com.jackson.jfood.api.assembler;

import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jackson.jfood.api.model.RoleModel;
import com.jackson.jfood.domain.model.Role;

@Component
public class RoleModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public RoleModel toModel(Role role) {
		return modelMapper.map(role, RoleModel.class);
	}
	
	public List<RoleModel> toCollectionModel(Collection<Role> roles) {
		return roles.stream().map(x -> toModel(x)).toList();
	}
}
