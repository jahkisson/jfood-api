package com.jackson.jfood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jackson.jfood.domain.model.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
