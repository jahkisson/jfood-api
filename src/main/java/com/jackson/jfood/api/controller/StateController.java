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

import com.jackson.jfood.api.assembler.StateInputDisassembler;
import com.jackson.jfood.api.assembler.StateModelAssembler;
import com.jackson.jfood.api.model.StateModel;
import com.jackson.jfood.api.model.input.StateInput;
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
	
	@Autowired
	private StateModelAssembler stateModelAssembler;
	
	@Autowired
	private StateInputDisassembler stateInputDisassembler;
	
	@GetMapping
	public List<StateModel> listar() {
		return stateModelAssembler.toCollectionModel(stateRepository.findAll());
	}
	
	@GetMapping("/{stateId}")
	public StateModel getById(@PathVariable("stateId") Long id) {
		return stateModelAssembler.toModel(stateRegistration.findByIdOrFail(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public StateModel add(@RequestBody @Valid StateInput stateInput) {
		State state = stateInputDisassembler.toDomainObject(stateInput);
		return stateModelAssembler.toModel(stateRegistration.save(state));
	}
	
	@PutMapping("/{stateId}")
	public StateModel update(@PathVariable("stateId") Long id, @RequestBody @Valid StateInput stateInput) {
		State stateToUpdate = stateRegistration.findByIdOrFail(id);
		stateInputDisassembler.copyToDomainObject(stateInput, stateToUpdate);
		return stateModelAssembler.toModel(stateRegistration.save(stateToUpdate));
	}
	
	@DeleteMapping("/{stateId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable("stateId") Long id) {
		stateRegistration.remove(id);
	}
}
