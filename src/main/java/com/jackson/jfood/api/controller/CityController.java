package com.jackson.jfood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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

import com.jackson.jfood.domain.exception.BusinessException;
import com.jackson.jfood.domain.exception.StateNotFoundException;
import com.jackson.jfood.domain.model.City;
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
		return cityRepository.findAll();
	}
	
	@GetMapping("/{cityId}")
	public City getById(@PathVariable Long cityId) {
		return cityRegistration.findByIdOrFail(cityId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public City add(@RequestBody @Valid City city) {
		try {
			return cityRegistration.save(city);
		} catch (StateNotFoundException ex) {
			throw new BusinessException(ex.getMessage(), ex);
		}
	}
	
	@PutMapping("/{cityId}")
	public City update(@PathVariable Long cityId, @RequestBody @Valid City city) {
		City cityToPersist = cityRegistration.findByIdOrFail(cityId);
		BeanUtils.copyProperties(city, cityToPersist, "id");
		try {
			return cityRegistration.save(cityToPersist);
		} catch (StateNotFoundException ex) {
			throw new BusinessException(ex.getMessage(), ex);
		}
	}
	
	@DeleteMapping("/{cityId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long cityId) {
		cityRegistration.remove(cityId);
	}
}
