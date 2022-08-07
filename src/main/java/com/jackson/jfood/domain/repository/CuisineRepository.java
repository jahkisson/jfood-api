package com.jackson.jfood.domain.repository;

import java.util.List;

import com.jackson.jfood.domain.model.Cuisine;

public interface CuisineRepository {

	List<Cuisine> list();
	Cuisine find(Long id);
	Cuisine persist(Cuisine cuisine);
	void remove(Cuisine cuisine);
}
