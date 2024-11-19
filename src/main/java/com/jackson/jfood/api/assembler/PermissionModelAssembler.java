package com.jackson.jfood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jackson.jfood.api.model.PermissionModel;
import com.jackson.jfood.domain.model.Permission;

@Component
public class PermissionModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public PermissionModel toModel(Permission permission) {
		return modelMapper.map(permission, PermissionModel.class);
	}
	
	public List<PermissionModel> toCollectionModel(Collection<Permission> list) {
		return list.stream().map(x -> toModel(x)).collect(Collectors.toList());
	}
	
}
