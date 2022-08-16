package com.jackson.jfood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jackson.jfood.domain.model.City;

public interface CityRepository extends JpaRepository<City, Long> {

}
