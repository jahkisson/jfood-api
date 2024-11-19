package com.jackson.jfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jackson.jfood.domain.exception.EntityIsBeingUsedException;
import com.jackson.jfood.domain.exception.PaymentTypeNotFoundException;
import com.jackson.jfood.domain.model.PaymentType;
import com.jackson.jfood.domain.repository.PaymentTypeRepository;

@Service
public class PaymentTypeRegistrationService {

	private static final String MESSAGE_PAYMENT_TYPE_IN_USE = "Forma de pagamento de código %d não pode ser removido pois está em uso";
	
	@Autowired
	private PaymentTypeRepository paymentTypeRepository;
	
	public PaymentType findByIdOrFail(Long paymentTypeId) {
		return paymentTypeRepository.findById(paymentTypeId)
				.orElseThrow(() -> new PaymentTypeNotFoundException(paymentTypeId));
	}
	
	@Transactional
	public PaymentType save(PaymentType paymentType) {
		return paymentTypeRepository.save(paymentType);
	}
	
	@Transactional
	public void remove(Long paymentTypeId) {
		try {
			paymentTypeRepository.deleteById(paymentTypeId);
			paymentTypeRepository.flush();
		} catch (EmptyResultDataAccessException ex) {
			throw new PaymentTypeNotFoundException(paymentTypeId);
		} catch (DataIntegrityViolationException ex) {
			throw new EntityIsBeingUsedException(String.format(MESSAGE_PAYMENT_TYPE_IN_USE, paymentTypeId));
		}
	}
}
