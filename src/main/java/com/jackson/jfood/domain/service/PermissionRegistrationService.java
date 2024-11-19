package com.jackson.jfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jackson.jfood.domain.exception.PermissionNotFoundException;
import com.jackson.jfood.domain.model.Permission;
import com.jackson.jfood.domain.repository.PermissionRepository;

@Service
public class PermissionRegistrationService {

	@Autowired
	private PermissionRepository permissionRepository;
	
	public Permission findByIdOrFail(Long permissionId) {
		return permissionRepository.findById(permissionId)
				.orElseThrow(() -> new PermissionNotFoundException(permissionId));
	}
}
