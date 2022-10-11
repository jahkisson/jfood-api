package com.jackson.jfood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jackson.jfood.api.model.StateModel;
import com.jackson.jfood.domain.model.State;

@Component
public class StateModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public StateModel toModel(State state) {
		return modelMapper.map(state, StateModel.class);
	}
	
	public List<StateModel> toCollectionModel(List<State> list) {
		return list.stream().map(x -> toModel(x)).collect(Collectors.toList());
	}
	
}
