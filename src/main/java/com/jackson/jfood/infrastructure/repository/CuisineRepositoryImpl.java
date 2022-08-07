package com.jackson.jfood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jackson.jfood.domain.model.Cuisine;
import com.jackson.jfood.domain.repository.CuisineRepository;

@Component
public class CuisineRepositoryImpl implements CuisineRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Cuisine> list() {
		return entityManager.createQuery("from Cuisine", Cuisine.class).getResultList();
	}
	
	@Transactional
	@Override
	public Cuisine persist(Cuisine cuisine) {
		return entityManager.merge(cuisine);
	}
	
	@Override
	public Cuisine find(Long id) {
		return entityManager.find(Cuisine.class, id);
	}
	
	@Transactional
	@Override
	public void remove(Cuisine cuisine) {
		cuisine = find(cuisine.getId());
		entityManager.remove(cuisine);
	}

}
