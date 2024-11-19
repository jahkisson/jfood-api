package com.jackson.jfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jackson.jfood.api.assembler.UserModelAssembler;
import com.jackson.jfood.api.model.UserModel;
import com.jackson.jfood.domain.model.Restaurant;
import com.jackson.jfood.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping("/restaurants/{restaurantId}/managers")
public class RestaurantUserManagerController {

	@Autowired
	private RestaurantRegistrationService restaurantRegistrationService;
	
	@Autowired
	private UserModelAssembler userModelAssembler;
	
	@GetMapping
	public List<UserModel> list(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantRegistrationService.findByIdOrFail(restaurantId);
		return userModelAssembler.toCollectionModel(restaurant.getManagers());
	}
	
	@PutMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void set(@PathVariable Long restaurantId, @PathVariable Long userId) {
		restaurantRegistrationService.setManager(restaurantId, userId);
	}
	
	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void unset(@PathVariable Long restaurantId, @PathVariable Long userId) {
		restaurantRegistrationService.unsetManager(restaurantId, userId);
	}
}
