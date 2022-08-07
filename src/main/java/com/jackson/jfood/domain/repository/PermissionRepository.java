package com.jackson.jfood.domain.repository;

import java.util.List;

import com.jackson.jfood.domain.model.Permission;

public interface PermissionRepository {

	List<Permission> list();
	Permission find(Long id);
	Permission persist(Permission permission);
	void remove(Permission permission);
}
