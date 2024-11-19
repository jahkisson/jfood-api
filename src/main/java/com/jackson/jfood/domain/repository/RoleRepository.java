package com.jackson.jfood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jackson.jfood.domain.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
