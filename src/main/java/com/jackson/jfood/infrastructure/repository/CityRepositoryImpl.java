package com.jackson.jfood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jackson.jfood.domain.model.City;
import com.jackson.jfood.domain.repository.CityRepository;

@Component
public class CityRepositoryImpl implements CityRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<City> list() {
		return entityManager.createQuery("from City", City.class).getResultList();
	}

	@Override
	public City find(Long id) {
		return entityManager.find(City.class, id);
	}

	@Transactional
	@Override
	public City persist(City city) {
		return entityManager.merge(city);
	}

	@Transactional
	@Override
	public void remove(Long id) {
		City city = find(id);
		if (city == null)
			throw new EmptyResultDataAccessException(1);
		entityManager.remove(city);
	}

}
