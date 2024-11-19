package com.jackson.jfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jackson.jfood.domain.exception.EntityIsBeingUsedException;
import com.jackson.jfood.domain.exception.RoleNotFoundException;
import com.jackson.jfood.domain.model.Permission;
import com.jackson.jfood.domain.model.Role;
import com.jackson.jfood.domain.repository.RoleRepository;

@Service
public class RoleRegistrationService {

	private final String MESSAGE_ROLE_IN_USE = "O grupo de código %d está em uso";
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PermissionRegistrationService permissionRegistrationService;
	
	public Role findOrFail(Long roleId) {
		return roleRepository.findById(roleId)
				.orElseThrow(() -> new RoleNotFoundException(roleId));
	}
	
	@Transactional
	public Role save(Role role) {
		return roleRepository.save(role);
	}
	
	@Transactional
	public void remove(Long id) {
		try {
			roleRepository.deleteById(id);
			roleRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new RoleNotFoundException(id);
		} catch (DataIntegrityViolationException ex) {
			throw new EntityIsBeingUsedException(String.format(MESSAGE_ROLE_IN_USE, id));
		}
	}
	
	@Transactional
	public void unsetPermission(Long roleId, Long permissionId) {
		Role role = findOrFail(roleId);
		Permission permission = permissionRegistrationService.findByIdOrFail(permissionId);
		role.removePermission(permission);
	}
	
	@Transactional
	public void setPermission(Long roleId, Long permissionId) {
		Role role = findOrFail(roleId);
		Permission permission = permissionRegistrationService.findByIdOrFail(permissionId);
		role.addPermission(permission);
	}
}
