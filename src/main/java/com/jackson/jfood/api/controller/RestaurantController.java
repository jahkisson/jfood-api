package com.jackson.jfood.api.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jackson.jfood.api.assembler.RestaurantInputDisassembler;
import com.jackson.jfood.api.assembler.RestaurantModelAssembler;
import com.jackson.jfood.api.model.RestaurantModel;
import com.jackson.jfood.api.model.input.RestaurantInput;
import com.jackson.jfood.domain.exception.BusinessException;
import com.jackson.jfood.domain.exception.EntityNotFoundException;
import com.jackson.jfood.domain.exception.RestaurantNotFoundException;
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
	
	@Autowired
	private RestaurantModelAssembler restaurantModelAssembler;
	
	@Autowired
	private RestaurantInputDisassembler restaurantInputDisassembler;
	
	@GetMapping
	public List<RestaurantModel> listar() {
		return restaurantModelAssembler.toCollectionModel(restaurantRepository.findAll());
	}
	
	@GetMapping("/{restaurantId}")
	public RestaurantModel getById(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantRegistration.findByIdOrFail(restaurantId);
		return restaurantModelAssembler.toModel(restaurant);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestaurantModel add(@RequestBody @Valid RestaurantInput restaurantInput) {
		try {
			Restaurant restaurant = restaurantInputDisassembler.toDomainObject(restaurantInput);
			return restaurantModelAssembler.toModel(restaurantRegistration.save(restaurant));
		} catch (EntityNotFoundException ex) {
			throw new BusinessException(ex.getMessage());
		}
	}
	
	@PutMapping("/{restaurantId}")
	public RestaurantModel update(@PathVariable Long restaurantId, @RequestBody @Valid RestaurantInput restaurantInput) {
		Restaurant restaurantToPersist = restaurantRegistration.findByIdOrFail(restaurantId);
		restaurantInputDisassembler.copyToDomainObject(restaurantInput, restaurantToPersist);
		try {
			return restaurantModelAssembler.toModel(restaurantRegistration.save(restaurantToPersist));
		} catch (EntityNotFoundException ex) {
			throw new BusinessException(ex.getMessage());
		}
	}
	
	@PutMapping("/{restaurantId}/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void activate(@PathVariable Long restaurantId) {
		restaurantRegistration.activate(restaurantId);
	}
	
	@DeleteMapping("/{restaurantId}/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deactivate(@PathVariable Long restaurantId) {
		restaurantRegistration.deactivate(restaurantId);
	}
	
	@PutMapping("/{restaurantId}/opening")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void open(@PathVariable Long restaurantId) {
		restaurantRegistration.open(restaurantId);
	}
	
	@PutMapping("/activations")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void activateMany(@RequestBody List<Long> restaurantIds) {
		try {
			restaurantRegistration.activate(restaurantIds);
		} catch (RestaurantNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/activations")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deactivateMany(@RequestBody List<Long> restaurantIds) {
		try {
			restaurantRegistration.deactivate(restaurantIds);
		} catch (RestaurantNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{restaurantId}/closing")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void close(@PathVariable Long restaurantId) {
		restaurantRegistration.close(restaurantId);
	}
}