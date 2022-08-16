package com.jackson.jfood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.jackson.jfood.domain.model.Cuisine;

@Repository
public interface CuisineRepository extends CustomJpaRepository<Cuisine, Long> {

	List<Cuisine> findByNameContaining(String name);
	Optional<Cuisine> findSingleByName(String name);
	boolean existsByName(String name);
}