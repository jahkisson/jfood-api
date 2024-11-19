package com.jackson.jfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jackson.jfood.domain.exception.ProductNotFoundException;
import com.jackson.jfood.domain.model.Product;
import com.jackson.jfood.domain.model.Restaurant;
import com.jackson.jfood.domain.repository.ProductRepository;

@Service
public class ProductRegistrationService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private RestaurantRegistrationService restaurantService;
	
	public Product findOrFailByRestaurantAndProduct(Long restaurantId, Long productId) {
		restaurantService.findByIdOrFail(restaurantId);
		return productRepository.findById(restaurantId, productId)
				.orElseThrow(() -> new ProductNotFoundException(restaurantId, productId));
	}
	
	@Transactional
	public Product saveProduct(Long restaurantId, Product product) {
		Restaurant restaurant = restaurantService.findByIdOrFail(restaurantId);
		product.setRestaurant(restaurant);
		return productRepository.save(product);
	}
}
