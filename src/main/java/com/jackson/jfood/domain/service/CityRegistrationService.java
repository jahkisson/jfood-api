package com.jackson.jfood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.jackson.jfood.domain.exception.EntityNotFoundException;
import com.jackson.jfood.domain.exception.ReferencedEntityNotFoundException;
import com.jackson.jfood.domain.model.City;
import com.jackson.jfood.domain.model.State;
import com.jackson.jfood.domain.repository.CityRepository;
import com.jackson.jfood.domain.repository.StateRepository;

@Service
public class CityRegistrationService {
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private StateRepository stateRepository;
	
	public City save(City city) {
		city = copyUpdatePropertiesIfNeeded(city);
		
		Long stateId = city.getState().getId();
		State state = stateRepository.findById(stateId)
				.orElseThrow(() -> new ReferencedEntityNotFoundException(String.format("O estado de código %d não foi encontrado", stateId)));
		
		city.setState(state);
		
		return cityRepository.save(city);
	}
	
	public void remove(Long id) {
		try {
			cityRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException(String.format("A cidade de código %d não foi encontrada", id));
		}
	}

	private City copyUpdatePropertiesIfNeeded(City city) {
		if (city.getId() == null || city.getId() <= 0)
			return city;
		
		City cityToPersist = cityRepository.findById(city.getId())
				.orElseThrow(() -> new EntityNotFoundException(String.format("A cidade de código %d não foi encontrada", city.getId())));
		
		BeanUtils.copyProperties(city, cityToPersist, "id");
		return cityToPersist;
	}
}
