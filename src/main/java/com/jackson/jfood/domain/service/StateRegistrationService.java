package com.jackson.jfood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.jackson.jfood.domain.exception.EntityIsBeingUsedException;
import com.jackson.jfood.domain.exception.EntityNotFoundException;
import com.jackson.jfood.domain.model.State;
import com.jackson.jfood.domain.repository.StateRepository;

@Service
public class StateRegistrationService {

	@Autowired
	private StateRepository stateRepository;
	
	public State save(State state) {
		state = copyUpdatePropertiesIfNeeded(state);
		return stateRepository.save(state);
	}
	
	public void remove(Long id) {
		try {
			stateRepository.deleteById(id);
		} catch (EmptyResultDataAccessException ex) {
			throw new EntityNotFoundException(String.format("Estado de código %d não encontrado", id));
		} catch (DataIntegrityViolationException ex) {
			throw new EntityIsBeingUsedException(String.format("Estado de código %d não pode ser removido pois está em uso", id));
		}
	}

	private State copyUpdatePropertiesIfNeeded(State state) {
		if (state.getId() == null || state.getId() <= 0)
			return state;
		
		State stateToPersist = stateRepository.findById(state.getId())
				.orElseThrow(() -> new EntityNotFoundException(String.format("O estado de código %d não foi encontrado", state.getId())));
		
		BeanUtils.copyProperties(state, stateToPersist, "id");
		return stateToPersist;
	}
}
