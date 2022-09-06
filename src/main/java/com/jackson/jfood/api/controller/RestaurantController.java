package com.jackson.jfood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jackson.jfood.domain.exception.BusinessException;
import com.jackson.jfood.domain.exception.EntityNotFoundException;
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
		return restaurantRepository.findAll();
	}
	
	@GetMapping("/{restaurantId}")
	public Restaurant getById(@PathVariable Long restaurantId) {
		return restaurantRegistration.findByIdOrFail(restaurantId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurant add(@RequestBody Restaurant restaurant) {
		try {
			return restaurantRegistration.save(restaurant);
		} catch (EntityNotFoundException ex) {
			throw new BusinessException(ex.getMessage());
		}
	}
	
	@PutMapping("/{restaurantId}")
	public Restaurant update(@PathVariable Long restaurantId, @RequestBody Restaurant restaurant) {
		Restaurant restaurantToPersist = restaurantRegistration.findByIdOrFail(restaurantId);
		BeanUtils.copyProperties(restaurant, restaurantToPersist, "id", "paymentTypes", "address", "creationTimestamp", "products");
		try {
			return restaurantRegistration.save(restaurantToPersist);
		} catch (EntityNotFoundException ex) {
			throw new BusinessException(ex.getMessage());
		}
	}
	
	@PatchMapping("/{restaurantId}")
	public Restaurant partialUpdate(@PathVariable Long restaurantId, 
			@RequestBody Map<String, Object> fields,
			HttpServletRequest request) {
		Restaurant oldRestaurant = restaurantRegistration.findByIdOrFail(restaurantId);
		merge(fields, oldRestaurant, request);
		oldRestaurant.setId(restaurantId);
		return update(restaurantId, oldRestaurant);
	}

	private void merge(Map<String, Object> fields, Restaurant restaurant, HttpServletRequest request) {
		ServletServerHttpRequest servHttpRequest = new ServletServerHttpRequest(request);
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			
			Restaurant sourceRestaurant = objectMapper.convertValue(fields, Restaurant.class);
			fields.forEach((key, value) -> {
				Field field = ReflectionUtils.findField(Restaurant.class, key);
				field.setAccessible(true);
				
				Object newValue = ReflectionUtils.getField(field, sourceRestaurant);
				ReflectionUtils.setField(field, restaurant, newValue);
			});
		} catch (IllegalArgumentException ex) {
			Throwable rootCause = ExceptionUtils.getRootCause(ex);
			throw new HttpMessageNotReadableException(ex.getMessage(), rootCause, servHttpRequest);
		}
	}
}