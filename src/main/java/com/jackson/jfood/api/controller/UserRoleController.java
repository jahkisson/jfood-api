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

import com.jackson.jfood.api.assembler.RoleModelAssembler;
import com.jackson.jfood.api.model.RoleModel;
import com.jackson.jfood.domain.model.User;
import com.jackson.jfood.domain.service.UserRegistrationService;

@RestController
@RequestMapping("/users/{userId}/roles")
public class UserRoleController {

	@Autowired
	private UserRegistrationService userRegistrationService;
	
	@Autowired
	private RoleModelAssembler roleModelAssembler;
	
	@GetMapping
	public List<RoleModel> list(@PathVariable Long userId) {
		User user = userRegistrationService.findOrFail(userId);
		return roleModelAssembler.toCollectionModel(user.getRoles());
	}
	
	@PutMapping("/{roleId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void set(@PathVariable Long userId, @PathVariable Long roleId) {
		userRegistrationService.setRole(userId, roleId);
	}
	
	@DeleteMapping("/{roleId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void unset(@PathVariable Long userId, @PathVariable Long roleId) {
		userRegistrationService.unsetRole(userId, roleId);
	}
}
