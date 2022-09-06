package com.jackson.jfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.jackson.jfood.domain.exception.CuisineNotFoundException;
import com.jackson.jfood.domain.exception.EntityIsBeingUsedException;
import com.jackson.jfood.domain.model.Cuisine;
import com.jackson.jfood.domain.repository.CuisineRepository;

@Service
public class CuisineRegistrationService {

	private static final String MESSAGE_CUISINE_IN_USE = "Cozinha de código %d não pode ser removida pois está em uso";
	
	@Autowired
	private CuisineRepository cuisineRepository;
	
	public Cuisine findByIdOrFail(Long cuisineId) {
		return cuisineRepository.findById(cuisineId)
				.orElseThrow(() -> new CuisineNotFoundException(cuisineId));
	}
	
	public Cuisine save(Cuisine cuisine) {
		return cuisineRepository.save(cuisine);
	}
	
	public void remove(Long cuisineId) {
		try {
			cuisineRepository.deleteById(cuisineId);
		} catch (EmptyResultDataAccessException ex) {
			throw new CuisineNotFoundException(cuisineId);
		} catch (DataIntegrityViolationException ex) {
			throw new EntityIsBeingUsedException(String.format(MESSAGE_CUISINE_IN_USE, cuisineId));
		}
	}
}
