package com.jackson.jfood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jackson.jfood.domain.exception.EntityIsBeingUsedException;
import com.jackson.jfood.domain.exception.EntityNotFoundException;
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
	public ResponseEntity<Cuisine> getById(@PathVariable Long cuisineId) {
		Optional<Cuisine> cuisine = cuisineRepository.findById(cuisineId);
		if (cuisine.isPresent())
			return ResponseEntity.ok(cuisine.get());
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cuisine add(@RequestBody Cuisine cuisine) {
		return cuisineRegistration.save(cuisine);
	}
	
	@PutMapping("/{cuisineId}")
	public ResponseEntity<Cuisine> update(@PathVariable Long cuisineId, @RequestBody Cuisine cuisine) {
		Optional<Cuisine> cuisineToUpdate = cuisineRepository.findById(cuisineId);
		if (cuisineToUpdate.isEmpty())
			return ResponseEntity.notFound().build();
		BeanUtils.copyProperties(cuisine, cuisineToUpdate.get(), "id");
		cuisine = cuisineRegistration.save(cuisineToUpdate.get());
		return ResponseEntity.ok(cuisine);
	}
	
	@DeleteMapping("/{cuisineId}")
	public ResponseEntity<Cuisine> delete(@PathVariable Long cuisineId) {
		try {
			cuisineRegistration.remove(cuisineId);
			return ResponseEntity.noContent().build();
		} catch (EntityIsBeingUsedException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (EntityNotFoundException ex) {
			return ResponseEntity.notFound().build();
		}
	}
}
