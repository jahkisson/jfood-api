package com.jackson.jfood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jackson.jfood.domain.model.Cuisine;
import com.jackson.jfood.domain.repository.CuisineRepository;
import com.jackson.jfood.domain.service.CuisineRegistrationService;

@RestController
@RequestMapping(value = "/cuisines")
public class CuisineController {

	@Autowired
	private CuisineRepository cuisineRepository;
	
	@Autowired
	private CuisineRegistrationService cuisineRegistration;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Cuisine> listar() {
		return cuisineRepository.findAll();
	}
	
	@GetMapping("/{cuisineId}")
	public Cuisine getById(@PathVariable Long cuisineId) {
		return cuisineRegistration.findByIdOrFail(cuisineId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cuisine add(@RequestBody @Valid Cuisine cuisine) {
		return cuisineRegistration.save(cuisine);
	}
	
	@PutMapping("/{cuisineId}")
	public Cuisine update(@PathVariable Long cuisineId, @RequestBody @Valid Cuisine cuisine) {
		Cuisine cuisineToUpdate = cuisineRegistration.findByIdOrFail(cuisineId);
		BeanUtils.copyProperties(cuisine, cuisineToUpdate, "id");
		return cuisineRegistration.save(cuisineToUpdate);
	}
	
	@DeleteMapping("/{cuisineId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long cuisineId) {
		cuisineRegistration.remove(cuisineId);
	}
}
