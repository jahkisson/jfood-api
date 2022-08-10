package com.jackson.jfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jackson.jfood.domain.exception.EntityNotFoundException;
import com.jackson.jfood.domain.exception.ReferencedEntityNotFoundException;
import com.jackson.jfood.domain.model.City;
import com.jackson.jfood.domain.model.State;
import com.jackson.jfood.domain.repository.CityRepository;
import com.jackson.jfood.domain.service.CityRegistrationService;

@RestController
@RequestMapping("/cities")
public class CityController {

	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private CityRegistrationService cityRegistration;
	
	@GetMapping
	public List<City> listar() {
		return cityRepository.list();
	}
	
	@GetMapping("/{cityId}")
	public City getById(@PathVariable Long cityId) {
		return cityRepository.find(cityId);
	}
	
	@PostMapping
	public ResponseEntity<?> add(@RequestBody City city) {
		try {
			city = cityRegistration.save(city);
			return ResponseEntity.status(HttpStatus.CREATED).body(city);
		} catch (ReferencedEntityNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{cityId}")
	public ResponseEntity<?> update(@PathVariable Long cityId, @RequestBody City city) {
		try {
			city.setId(cityId);
			city = cityRegistration.save(city);
			return ResponseEntity.ok(city);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (ReferencedEntityNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{cityId}")
	public ResponseEntity<State> update(@PathVariable Long cityId) {
		try {
			cityRegistration.remove(cityId);
			return ResponseEntity.noContent().build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
