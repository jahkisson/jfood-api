package com.jackson.jfood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jackson.jfood.domain.model.Product;
import com.jackson.jfood.domain.model.Restaurant;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByRestaurant(Restaurant restaurant);
	
	@Query("from Product where restaurant.id = :restaurant and id = :produto")
	Optional<Product> findById(@Param("restaurant") Long restaurantId, @Param("produto") Long productId);
}
