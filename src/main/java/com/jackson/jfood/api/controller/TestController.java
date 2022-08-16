package com.jackson.jfood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jackson.jfood.domain.model.Cuisine;
import com.jackson.jfood.domain.model.Restaurant;
import com.jackson.jfood.domain.repository.CuisineRepository;
import com.jackson.jfood.domain.repository.RestaurantRepository;

@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	private CuisineRepository cuisineRepository;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@GetMapping("/cuisines/by-name")
	public List<Cuisine> listByName(@RequestParam String name) {
		return cuisineRepository.findByNameContaining(name);
	}
	
	@GetMapping("/cuisines/exists-by-name")
	public boolean existsByName(@RequestParam String name) {
		return cuisineRepository.existsByName(name);
	}
	
	@GetMapping("/restaurants/by-shipping-fee")
	public List<Restaurant> listByShippingFeeBetween(@RequestParam BigDecimal start, @RequestParam BigDecimal end) {
		return restaurantRepository.findByShippingFeeBetween(start, end);
	}
	
	@GetMapping("/restaurants/by-name-and-shipping-fee")
	public List<Restaurant> listByNameAndShippingFeeBetween(@RequestParam(required = false) String name, @RequestParam(required = false) BigDecimal start, @RequestParam(required = false) BigDecimal end) {
		return restaurantRepository.find(name, start, end);
	}
	
	@GetMapping("/restaurants/by-name-and-cuisine")
	public List<Restaurant> listByNameAndCuisine(@RequestParam String name, @RequestParam Long cuisineId) {
		return restaurantRepository.queryByName(name, cuisineId);
	}
	
	@GetMapping("/restaurants/first-by-name")
	public Optional<Restaurant> firstByName(@RequestParam String name) {
		return restaurantRepository.findFirstRestByNameContaining(name);
	}
	
	@GetMapping("/restaurants/first-top2-by-name")
	public List<Restaurant> top2ByName(@RequestParam String name) {
		return restaurantRepository.findTop2ByNameContaining(name);
	}
	
	@GetMapping("/restaurants/count-by-cuisine")
	public int countByCuisine(@RequestParam Long cuisineId) {
		return restaurantRepository.countByCuisineId(cuisineId);
	}
	
	@GetMapping("/restaurants/free-shipping")
	public List<Restaurant> restaurantsWithFreeShipping(@RequestParam String name) {
		return restaurantRepository.findWithFreeShipping(name);
	}
	
	@GetMapping("/restaurants/first")
	public Optional<Restaurant> restaurantsFirst() {
		return restaurantRepository.findFirst();
	}
}
