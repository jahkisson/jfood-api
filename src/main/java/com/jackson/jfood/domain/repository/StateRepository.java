package com.jackson.jfood.domain.repository;

import java.util.List;

import com.jackson.jfood.domain.model.State;

public interface StateRepository {

	List<State> list();
	State find(Long id);
	State persist(State state);
	void remove(Long id);
}
