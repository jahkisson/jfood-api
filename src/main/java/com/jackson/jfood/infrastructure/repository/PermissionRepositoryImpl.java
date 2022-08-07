package com.jackson.jfood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jackson.jfood.domain.model.Permission;
import com.jackson.jfood.domain.repository.PermissionRepository;

@Component
public class PermissionRepositoryImpl implements PermissionRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Permission> list() {
		return entityManager.createQuery("from Permission", Permission.class).getResultList();
	}

	@Override
	public Permission find(Long id) {
		return entityManager.find(Permission.class, id);
	}

	@Transactional
	@Override
	public Permission persist(Permission permission) {
		return entityManager.merge(permission);
	}

	@Transactional
	@Override
	public void remove(Permission permission) {
		permission = find(permission.getId());
		entityManager.remove(permission);
	}

}
