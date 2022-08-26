package com.jackson.jfood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jackson.jfood.domain.model.Restaurant;

@Repository
public interface RestaurantRepository extends CustomJpaRepository<Restaurant, Long>, RestaurantRepositoryQueries, JpaSpecificationExecutor<Restaurant> {

	@Query("from Restaurant r join fetch r.cuisine")
	List<Restaurant> findAll();
	
	List<Restaurant> findByShippingFeeBetween(BigDecimal start, BigDecimal end);
	
	//@Query("from Restaurant where name like %:name% and cuisine.id = :cuisine_Id")
	List<Restaurant> queryByName(String name, @Param("cuisine_Id") Long cuisineId);
	//List<Restaurant> findByNameContainingAndCuisineId(String name, Long cuisineId);
	
	Optional<Restaurant> findFirstRestByNameContaining(String name);
	
	List<Restaurant> findTop2ByNameContaining(String name);
	
	int countByCuisineId(Long id);
}
