package com.jackson.jfood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jackson.jfood.domain.exception.EntityIsBeingUsedException;
import com.jackson.jfood.domain.exception.StateNotFoundException;
import com.jackson.jfood.domain.model.State;
import com.jackson.jfood.domain.repository.StateRepository;

@Service
public class StateRegistrationService {

	private static final String MESSAGE_STATE_IN_USE = "Estado de código %d não pode ser removido pois está em uso";
	
	@Autowired
	private StateRepository stateRepository;
	
	public State findByIdOrFail(Long stateId) {
		return stateRepository.findById(stateId)
				.orElseThrow(() -> new StateNotFoundException(stateId));
	}
	
	@Transactional
	public State save(State state) {
		state = copyUpdatePropertiesIfNeeded(state);
		return stateRepository.save(state);
	}
	
	@Transactional
	public void remove(Long id) {
		try {
			stateRepository.deleteById(id);
			stateRepository.flush();
		} catch (EmptyResultDataAccessException ex) {
			throw new StateNotFoundException(id);
		} catch (DataIntegrityViolationException ex) {
			throw new EntityIsBeingUsedException(String.format(MESSAGE_STATE_IN_USE, id));
		}
	}

	private State copyUpdatePropertiesIfNeeded(State state) {
		if (state.getId() == null || state.getId() <= 0)
			return state;
		
		State stateToPersist = findByIdOrFail(state.getId());
		
		BeanUtils.copyProperties(state, stateToPersist, "id");
		return stateToPersist;
	}
}
