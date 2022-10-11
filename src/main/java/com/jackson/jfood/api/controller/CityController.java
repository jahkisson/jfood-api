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

import com.jackson.jfood.api.assembler.CityInputDisassembler;
import com.jackson.jfood.api.assembler.CityModelAssembler;
import com.jackson.jfood.api.model.CityModel;
import com.jackson.jfood.api.model.input.CityInput;
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
	
	@Autowired
	private CityModelAssembler cityModelAssembler;
	
	@Autowired
	private CityInputDisassembler cityInputDisassembler;
	
	@GetMapping
	public List<CityModel> listar() {
		return cityModelAssembler.toCollectionModel(cityRepository.findAll());
	}
	
	@GetMapping("/{cityId}")
	public CityModel getById(@PathVariable Long cityId) {
		return cityModelAssembler.toModel(cityRegistration.findByIdOrFail(cityId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CityModel add(@RequestBody @Valid CityInput cityInput) {
		try {
			City city = cityInputDisassembler.toDomainObject(cityInput);
			return cityModelAssembler.toModel(cityRegistration.save(city));
		} catch (StateNotFoundException ex) {
			throw new BusinessException(ex.getMessage(), ex);
		}
	}
	
	@PutMapping("/{cityId}")
	public CityModel update(@PathVariable Long cityId, @RequestBody @Valid CityInput cityInput) {
		City cityToPersist = cityRegistration.findByIdOrFail(cityId);
		cityInputDisassembler.copyToDomainObject(cityInput, cityToPersist);
		try {
			return cityModelAssembler.toModel(cityRegistration.save(cityToPersist));
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
