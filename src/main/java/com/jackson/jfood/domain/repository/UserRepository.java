package com.jackson.jfood.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.jackson.jfood.domain.model.User;

@Repository
public interface UserRepository extends CustomJpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
}
