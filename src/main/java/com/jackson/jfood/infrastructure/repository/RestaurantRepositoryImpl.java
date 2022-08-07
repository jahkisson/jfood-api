package com.jackson.jfood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jackson.jfood.domain.model.Restaurant;
import com.jackson.jfood.domain.repository.RestaurantRepository;

@Component
public class RestaurantRepositoryImpl implements RestaurantRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Restaurant> list() {
		return entityManager.createQuery("from Restaurant", Restaurant.class).getResultList();
	}
	
	@Transactional
	@Override
	public Restaurant persist(Restaurant restaurant) {
		return entityManager.merge(restaurant);
	}
	
	@Override
	public Restaurant find(Long id) {
		return entityManager.find(Restaurant.class, id);
	}
	
	@Transactional
	@Override
	public void remove(Restaurant restaurant) {
		restaurant = find(restaurant.getId());
		entityManager.remove(restaurant);
	}

}
