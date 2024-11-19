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

import com.jackson.jfood.api.assembler.PaymentTypeModelAssembler;
import com.jackson.jfood.api.model.PaymentTypeModel;
import com.jackson.jfood.domain.model.Restaurant;
import com.jackson.jfood.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping(value = "/restaurants/{restaurantId}/payment-types")
public class RestaurantPaymentTypeController {

	@Autowired
	private RestaurantRegistrationService restaurantService;
	
	@Autowired
	private PaymentTypeModelAssembler paymentTypeModelAssembler;
	
	@GetMapping
	public List<PaymentTypeModel> listar(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantService.findByIdOrFail(restaurantId);
		return paymentTypeModelAssembler.toCollectionModel(restaurant.getPaymentTypes());
	}
	
	@DeleteMapping("/{paymentTypeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void detach(@PathVariable Long restaurantId, @PathVariable Long paymentTypeId) {
		restaurantService.detachPaymentType(restaurantId, paymentTypeId);
	}
	
	@PutMapping("/{paymentTypeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void attach(@PathVariable Long restaurantId, @PathVariable Long paymentTypeId) {
		restaurantService.attachPaymentType(restaurantId, paymentTypeId);
	}
}