package com.jackson.jfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jackson.jfood.domain.model.State;
import com.jackson.jfood.domain.repository.StateRepository;

@RestController
@RequestMapping("/states")
public class StateController {

	@Autowired
	private StateRepository stateRepository;
	
	@GetMapping
	public List<State> listar() {
		return stateRepository.list();
	}
	
	@GetMapping("/{stateId}")
	public State getById(@PathVariable("stateId") Long id) {
		return stateRepository.find(id);
	}
}
