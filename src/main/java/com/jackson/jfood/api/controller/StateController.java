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

import com.jackson.jfood.domain.exception.EntityIsBeingUsedException;
import com.jackson.jfood.domain.exception.EntityNotFoundException;
import com.jackson.jfood.domain.model.State;
import com.jackson.jfood.domain.repository.StateRepository;
import com.jackson.jfood.domain.service.StateRegistrationService;

@RestController
@RequestMapping("/states")
public class StateController {

	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private StateRegistrationService stateRegistration;
	
	@GetMapping
	public List<State> listar() {
		return stateRepository.list();
	}
	
	@GetMapping("/{stateId}")
	public State getById(@PathVariable("stateId") Long id) {
		return stateRepository.find(id);
	}
	
	@PostMapping
	public ResponseEntity<State> add(@RequestBody State state) {
		state = stateRegistration.save(state);
		return ResponseEntity.status(HttpStatus.CREATED).body(state);
	}
	
	@PutMapping("/{stateId}")
	public ResponseEntity<State> update(@PathVariable("stateId") Long id, @RequestBody State state) {
		try {
			state.setId(id);
			state = stateRegistration.save(state);
			return ResponseEntity.ok(state);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{stateId}")
	public ResponseEntity<State> update(@PathVariable("stateId") Long id) {
		try {
			stateRegistration.remove(id);
			return ResponseEntity.noContent().build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (EntityIsBeingUsedException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
