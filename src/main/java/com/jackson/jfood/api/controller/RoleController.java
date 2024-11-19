package com.jackson.jfood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jackson.jfood.api.assembler.RoleInputDisassembler;
import com.jackson.jfood.api.assembler.RoleModelAssembler;
import com.jackson.jfood.api.model.RoleModel;
import com.jackson.jfood.api.model.input.RoleInput;
import com.jackson.jfood.domain.model.Role;
import com.jackson.jfood.domain.repository.RoleRepository;
import com.jackson.jfood.domain.service.RoleRegistrationService;

@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RoleRegistrationService registrationService;
	
	@Autowired
	private RoleInputDisassembler roleInputDisassembler;
	
	@Autowired
	private RoleModelAssembler roleModelAssembler;
	
	@GetMapping
	public List<RoleModel> listAll() {
		return roleModelAssembler.toCollectionModel(roleRepository.findAll());
	}
	
	@GetMapping("/{roleId}")
	public RoleModel getById(@PathVariable Long roleId) {
		return roleModelAssembler.toModel(registrationService.findOrFail(roleId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RoleModel add(@RequestBody @Valid RoleInput roleInput) {
		Role role = roleInputDisassembler.toDomainObject(roleInput);
		return roleModelAssembler.toModel(registrationService.save(role));
	}
	
	@PutMapping("/{roleId}")
	public RoleModel update(@PathVariable("roleId") Long id, @RequestBody @Valid RoleInput roleInput) {
		Role roleToUpdate = registrationService.findOrFail(id);
		roleInputDisassembler.copyToDomainObject(roleInput, roleToUpdate);
		return roleModelAssembler.toModel(registrationService.save(roleToUpdate));
	}
	
	@DeleteMapping("/{roleId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long roleId) {
		registrationService.remove(roleId);
	}
}
