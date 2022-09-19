package com.jackson.jfood.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

public class ValueZeroIncludesDescriptionValidator implements ConstraintValidator<ValueZeroIncludesDescription, Object> {

	private String fieldValue;
	private String fieldDescription;
	private String mandatoryDescription;
	
	@Override
	public void initialize(ValueZeroIncludesDescription constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
		this.fieldValue = constraintAnnotation.fieldValue();
		this.fieldDescription = constraintAnnotation.fieldDescription();
		this.mandatoryDescription = constraintAnnotation.mandatoryDescription();
	}
	
	@Override
	public boolean isValid(Object validationObject, ConstraintValidatorContext context) {
		boolean valid = true;
		
		try {
			BigDecimal value = (BigDecimal) BeanUtils.getPropertyDescriptor(validationObject.getClass(), this.fieldValue)
					.getReadMethod().invoke(validationObject);
			String description = (String) BeanUtils.getPropertyDescriptor(validationObject.getClass(), this.fieldDescription)
					.getReadMethod().invoke(validationObject);
			
			if (value != null && BigDecimal.ZERO.compareTo(value) == 0 && description != null) {
				valid = description.toLowerCase().contains(this.mandatoryDescription.toLowerCase());
			}
		} catch (Exception e) {
			throw new ValidationException(e);
		}
		
		return valid;
	}

}