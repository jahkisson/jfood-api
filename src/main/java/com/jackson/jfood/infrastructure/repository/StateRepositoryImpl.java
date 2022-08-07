package com.jackson.jfood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jackson.jfood.domain.model.State;
import com.jackson.jfood.domain.repository.StateRepository;

@Component
public class StateRepositoryImpl implements StateRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<State> list() {
		return entityManager.createQuery("from State", State.class).getResultList();
	}

	@Override
	public State find(Long id) {
		return entityManager.find(State.class, id);
	}

	@Transactional
	@Override
	public State persist(State state) {
		return entityManager.merge(state);
	}

	@Transactional
	@Override
	public void remove(State state) {
		state = find(state.getId());
		entityManager.remove(state);
	}

}
