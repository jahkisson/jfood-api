package com.jackson.jfood.domain.repository;

import java.util.List;

import com.jackson.jfood.domain.model.City;

public interface CityRepository {

	List<City> list();
	City find(Long id);
	City persist(City city);
	void remove(Long id);
}
