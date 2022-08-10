package com.jackson.jfood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jackson.jfood.domain.exception.EntityNotFoundException;
import com.jackson.jfood.domain.exception.ReferencedEntityNotFoundException;
import com.jackson.jfood.domain.model.Restaurant;
import com.jackson.jfood.domain.repository.RestaurantRepository;
import com.jackson.jfood.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping(value = "/restaurants")
public class RestaurantController {

	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private RestaurantRegistrationService restaurantRegistration;
	
	@GetMapping
	public List<Restaurant> listar() {
		return restaurantRepository.list();
	}
	
	@GetMapping("/{restaurantId}")
	public ResponseEntity<Restaurant> getById(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantRepository.find(restaurantId);
		if (restaurant != null)
			return ResponseEntity.ok(restaurant);
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> add(@RequestBody Restaurant restaurant) {
		try {
			restaurant = restaurantRegistration.save(restaurant);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
		} catch (ReferencedEntityNotFoundException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
	
	@PutMapping("/{restaurantId}")
	public ResponseEntity<?> update(@PathVariable Long restaurantId, @RequestBody Restaurant restaurant) {
		try {
			restaurant.setId(restaurantId);
			restaurant = restaurantRegistration.save(restaurant);
			return ResponseEntity.ok(restaurant);
		} catch (ReferencedEntityNotFoundException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		} catch (EntityNotFoundException ex) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PatchMapping("/{restaurantId}")
	public ResponseEntity<?> partialUpdate(@PathVariable Long restaurantId, @RequestBody Map<String, Object> fields) {
		Restaurant oldRestaurant = restaurantRepository.find(restaurantId);
		merge(fields, oldRestaurant);
		oldRestaurant.setId(restaurantId);
		return update(restaurantId, oldRestaurant);
	}

	private void merge(Map<String, Object> fields, Restaurant restaurant) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurant sourceRestaurant = objectMapper.convertValue(fields, Restaurant.class);
		fields.forEach((key, value) -> {
			Field field = ReflectionUtils.findField(Restaurant.class, key);
			field.setAccessible(true);
			
			Object newValue = ReflectionUtils.getField(field, sourceRestaurant);
			ReflectionUtils.setField(field, restaurant, newValue);
		});
	}
}