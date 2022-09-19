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
		return stateRepository.findAll();
	}
	
	@GetMapping("/{stateId}")
	public State getById(@PathVariable("stateId") Long id) {
		return stateRegistration.findByIdOrFail(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public State add(@RequestBody @Valid State state) {
		return stateRegistration.save(state);
	}
	
	@PutMapping("/{stateId}")
	public State update(@PathVariable("stateId") Long id, @RequestBody @Valid State state) {
		State stateToUpdate = stateRegistration.findByIdOrFail(id);
		BeanUtils.copyProperties(state, stateToUpdate, "id");
		return stateRegistration.save(state);
	}
	
	@DeleteMapping("/{stateId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable("stateId") Long id) {
		stateRegistration.remove(id);
	}
}
