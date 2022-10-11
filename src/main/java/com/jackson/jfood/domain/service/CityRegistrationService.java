package com.jackson.jfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jackson.jfood.domain.exception.CityNotFoundException;
import com.jackson.jfood.domain.exception.EntityIsBeingUsedException;
import com.jackson.jfood.domain.model.City;
import com.jackson.jfood.domain.model.State;
import com.jackson.jfood.domain.repository.CityRepository;

@Service
public class CityRegistrationService {
	
	private final String MESSAGE_CITY_IN_USE = "A cidade de código %d está em uso";

	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private StateRegistrationService stateRegistration;
	
	public City findByIdOrFail(Long cityId) {
		return cityRepository.findById(cityId)
				.orElseThrow(() -> new CityNotFoundException(cityId));
	}
	
	@Transactional
	public City save(City city) {
		Long stateId = city.getState().getId();
		State state = stateRegistration.findByIdOrFail(stateId);
		city.setState(state);
		
		return cityRepository.save(city);
	}
	
	@Transactional
	public void remove(Long id) {
		try {
			cityRepository.deleteById(id);
			cityRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new CityNotFoundException(id);
		} catch (DataIntegrityViolationException ex) {
			throw new EntityIsBeingUsedException(String.format(MESSAGE_CITY_IN_USE, id));
		}
	}
}
