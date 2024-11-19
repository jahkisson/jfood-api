package com.jackson.jfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jackson.jfood.api.assembler.PermissionModelAssembler;
import com.jackson.jfood.api.model.PermissionModel;
import com.jackson.jfood.domain.model.Role;
import com.jackson.jfood.domain.service.RoleRegistrationService;

@RestController
@RequestMapping("/roles/{roleId}/permissions")
public class RolePermissionController {

	@Autowired
	private RoleRegistrationService roleRegistrationService;
	
	@Autowired
	private PermissionModelAssembler permissionModelAssembler;
	
	@GetMapping
	public List<PermissionModel> list(@PathVariable Long roleId) {
		Role role = roleRegistrationService.findOrFail(roleId);
		return permissionModelAssembler.toCollectionModel(role.getPermissions());
	}
	
	@DeleteMapping("/{permissionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void unset(@PathVariable Long roleId, @PathVariable Long permissionId) {
		roleRegistrationService.unsetPermission(roleId, permissionId);
	}
	
	@PutMapping("/{permissionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void set(@PathVariable Long roleId, @PathVariable Long permissionId) {
		roleRegistrationService.setPermission(roleId, permissionId);
	}
}
