package com.jackson.jfood;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jackson.jfood.domain.exception.EntityIsBeingUsedException;
import com.jackson.jfood.domain.exception.EntityNotFoundException;
import com.jackson.jfood.domain.model.Cuisine;
import com.jackson.jfood.domain.service.CuisineRegistrationService;

@SpringBootTest
class CuisineRegistrationIT {

	@Autowired
	CuisineRegistrationService cuisineRegistration;
	
	@Test
	void shouldAssignId_WhenCuisineRegistrationWithCorrectData() {
		Cuisine newCuisine = new Cuisine();
		newCuisine.setName("Chinese");
		
		// action
		newCuisine = cuisineRegistration.save(newCuisine);
		
		// assertions
		assertThat(newCuisine).isNotNull();
		assertThat(newCuisine.getId()).isNotNull();
	}

	@Test
	void shouldFail_WhenCuisineRegistrationWithEmptyName() {
		Cuisine newCuisine = new Cuisine();
		
		// action
		ConstraintViolationException expectedError = Assertions.assertThrows(
				ConstraintViolationException.class, () -> cuisineRegistration.save(newCuisine));
		
		
		assertThat(expectedError).isNotNull();
	}
	
	@Test
	void shouldFail_WhenRemovingCuisineInUse() {
		long cuisineId = 1L;
		
		// action
		EntityIsBeingUsedException expectedError = Assertions.assertThrows(
				EntityIsBeingUsedException.class, () -> cuisineRegistration.remove(cuisineId));
		
		assertThat(expectedError).isNotNull();
	}
	
	@Test
	void shouldFail_WhenRemovingNonExistingCuisine() {
		long cuisineId = 99L;
		
		// action
		EntityNotFoundException expectedError = Assertions.assertThrows(
				EntityNotFoundException.class, () -> cuisineRegistration.remove(cuisineId));
		
		assertThat(expectedError).isNotNull();
	}
}
