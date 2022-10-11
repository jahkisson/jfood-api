package com.jackson.jfood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jackson.jfood.api.model.input.StateInput;
import com.jackson.jfood.domain.model.State;

@Component
public class StateInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public State toDomainObject(StateInput stateInput) {
		return modelMapper.map(stateInput, State.class);
	}
	
	public void copyToDomainObject(StateInput stateInput, State state) {
		modelMapper.map(stateInput, state);
	}
}
