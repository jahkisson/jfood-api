package com.jackson.jfood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jackson.jfood.domain.model.Order;

@Repository
public interface OrderRepository extends CustomJpaRepository<Order, Long>{

	@Query("from Order o join fetch o.customer join fetch o.restaurant r join fetch r.cuisine")
	List<Order> findAll();
	
	Optional<Order> findByCode(String code);
	
}
