package com.jackson.jfood.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.jackson.jfood.api.assembler.CuisineInputDisassembler;
import com.jackson.jfood.api.assembler.CuisineModelAssembler;
import com.jackson.jfood.api.model.CuisineModel;
import com.jackson.jfood.api.model.input.CuisineInput;
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
	
	@Autowired
	private CuisineInputDisassembler cuisineInputDisassembler;
	
	@Autowired
	private CuisineModelAssembler cuisineModelAssembler;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CuisineModel> listar() {
		return cuisineModelAssembler.toCollectionModel(cuisineRepository.findAll());
	}
	
	@GetMapping("/{cuisineId}")
	public CuisineModel getById(@PathVariable Long cuisineId) {
		return cuisineModelAssembler.toModel(cuisineRegistration.findByIdOrFail(cuisineId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CuisineModel add(@RequestBody @Valid CuisineInput cuisineInput) {
		Cuisine cuisine = cuisineInputDisassembler.toDomainObject(cuisineInput);
		return cuisineModelAssembler.toModel(cuisineRegistration.save(cuisine));
	}
	
	@PutMapping("/{cuisineId}")
	public CuisineModel update(@PathVariable Long cuisineId, @RequestBody @Valid CuisineInput cuisineInput) {
		Cuisine cuisineToUpdate = cuisineRegistration.findByIdOrFail(cuisineId);
		cuisineInputDisassembler.copyToDomainObject(cuisineInput, cuisineToUpdate);
		return cuisineModelAssembler.toModel(cuisineRegistration.save(cuisineToUpdate));
	}
	
	@DeleteMapping("/{cuisineId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long cuisineId) {
		cuisineRegistration.remove(cuisineId);
	}
}
