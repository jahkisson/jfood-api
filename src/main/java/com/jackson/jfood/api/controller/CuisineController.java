package com.jackson.jfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jackson.jfood.domain.model.Cuisine;
import com.jackson.jfood.domain.repository.CuisineRepository;

@RestController
@RequestMapping(value = "/cuisines", produces = MediaType.APPLICATION_XML_VALUE)
public class CuisineController {

	@Autowired
	private CuisineRepository cuisineRepository;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Cuisine> listar() {
		return cuisineRepository.list();
	}
	
	@GetMapping("/{cuisineId}")
	public Cuisine getById(@PathVariable Long cuisineId) {
		return cuisineRepository.find(cuisineId);
	}
}
