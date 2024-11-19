package com.jackson.jfood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jackson.jfood.domain.exception.RestaurantNotFoundException;
import com.jackson.jfood.domain.model.City;
import com.jackson.jfood.domain.model.Cuisine;
import com.jackson.jfood.domain.model.PaymentType;
import com.jackson.jfood.domain.model.Restaurant;
import com.jackson.jfood.domain.model.User;
import com.jackson.jfood.domain.repository.RestaurantRepository;

@Service
public class RestaurantRegistrationService {

	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private CuisineRegistrationService cuisineRegistration;
	
	@Autowired
	private CityRegistrationService cityRegistrationService;
	
	@Autowired
	private PaymentTypeRegistrationService paymentTypeRegistrationService;
	
	@Autowired
	private UserRegistrationService userRegistrationService;
	
	@Transactional
	public void activate(Long restaurantId) {
		Restaurant restaurant = findByIdOrFail(restaurantId);
		restaurant.activate();
	}
	
	@Transactional
	public void deactivate(Long restaurantId) {
		Restaurant restaurant = findByIdOrFail(restaurantId);
		restaurant.deactivate();
	}
	
	@Transactional
	public void activate(List<Long> restaurantIds) {
		restaurantIds.forEach(this::activate);
	}
	
	@Transactional
	public void deactivate(List<Long> restaurantIds) {
		restaurantIds.forEach(this::deactivate);
	}
	
	public Restaurant findByIdOrFail(Long restaurantId) {
		return restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
	}
	
	@Transactional
	public Restaurant save(Restaurant restaurant) {
		Long cuisineId = restaurant.getCuisine().getId();
		Cuisine cuisine = cuisineRegistration.findByIdOrFail(cuisineId);
		restaurant.setCuisine(cuisine);
		
		if (restaurant.getAddress() != null) {
			Long cityId = restaurant.getAddress().getCity().getId();
			City city = cityRegistrationService.findByIdOrFail(cityId);
			restaurant.getAddress().setCity(city);
		}
		
		
		return restaurantRepository.save(restaurant);
	}
	
	@Transactional
	public void detachPaymentType(Long restaurantId, Long paymentTypeId) {
		Restaurant restaurant = findByIdOrFail(restaurantId);
		PaymentType paymentType = paymentTypeRegistrationService.findByIdOrFail(paymentTypeId);
		restaurant.getPaymentTypes().remove(paymentType);
	}
	
	@Transactional
	public void attachPaymentType(Long restaurantId, Long paymentTypeId) {
		Restaurant restaurant = findByIdOrFail(restaurantId);
		PaymentType paymentType = paymentTypeRegistrationService.findByIdOrFail(paymentTypeId);
		
		restaurant.getPaymentTypes().add(paymentType);
	}
	
	@Transactional
	public void open(Long restaurantId) {
		Restaurant restaurant = findByIdOrFail(restaurantId);
		restaurant.toOpen();
	}
	
	@Transactional
	public void close(Long restaurantId) {
		Restaurant restaurant = findByIdOrFail(restaurantId);
		restaurant.toClose();
	}
	
	@Transactional
	public void setManager(Long restaurantId, Long userId) {
		Restaurant restaurant = findByIdOrFail(restaurantId);
		User user = userRegistrationService.findOrFail(userId);
		restaurant.addManager(user);
	}
	
	@Transactional
	public void unsetManager(Long restaurantId, Long userId) {
		Restaurant restaurant = findByIdOrFail(restaurantId);
		User user = userRegistrationService.findOrFail(userId);
		restaurant.removeManager(user);
	}
}
