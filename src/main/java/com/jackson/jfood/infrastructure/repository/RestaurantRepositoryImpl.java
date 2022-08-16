package com.jackson.jfood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.jackson.jfood.domain.model.Restaurant;
import com.jackson.jfood.domain.repository.RestaurantRepository;
import com.jackson.jfood.domain.repository.RestaurantRepositoryQueries;
import com.jackson.jfood.infrastructure.repository.spec.RestaurantSpecs;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired @Lazy
	private RestaurantRepository restaurantRepository;
	
	@Override
	public List<Restaurant> find(String name, BigDecimal initialShippingFee, BigDecimal finalShippingFee) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Restaurant> criteria = builder.createQuery(Restaurant.class);
		Root<Restaurant> root = criteria.from(Restaurant.class);
		var predicates = new ArrayList<Predicate>();
		
		if (StringUtils.hasLength(name)) {
			predicates.add(builder.like(root.get("name"), "%" + name + "%"));
		}
		if (initialShippingFee != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("shippingFee"), initialShippingFee));
		}
		if (finalShippingFee != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("shippingFee"), finalShippingFee));
		}
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		return entityManager.createQuery(criteria).getResultList();
	}

	@Override
	public List<Restaurant> findWithFreeShipping(String name) {
		return restaurantRepository.findAll(RestaurantSpecs.withFreeShipping().and(RestaurantSpecs.withNameLike(name)));
	}
}
