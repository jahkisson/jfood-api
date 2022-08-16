package com.jackson.jfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.jackson.jfood.domain.exception.EntityIsBeingUsedException;
import com.jackson.jfood.domain.exception.EntityNotFoundException;
import com.jackson.jfood.domain.model.Cuisine;
import com.jackson.jfood.domain.repository.CuisineRepository;

@Service
public class CuisineRegistrationService {

	@Autowired
	private CuisineRepository cuisineRepository;
	
	public Cuisine save(Cuisine cuisine) {
		return cuisineRepository.save(cuisine);
	}
	
	public void remove(Long cuisineId) {
		try {
			cuisineRepository.deleteById(cuisineId);
		} catch (EmptyResultDataAccessException ex) {
			throw new EntityNotFoundException(String.format("Cozinha de código %d não encontrada", cuisineId));
		} catch (DataIntegrityViolationException ex) {
			throw new EntityIsBeingUsedException(String.format("Cozinha de código %d não pode ser removida pois está em uso", cuisineId));
		}
	}
}
