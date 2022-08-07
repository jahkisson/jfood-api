package com.jackson.jfood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jackson.jfood.domain.model.PaymentType;
import com.jackson.jfood.domain.repository.PaymentTypeRepository;

@Component
public class PaymentTypeRepositoryImpl implements PaymentTypeRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<PaymentType> list() {
		return entityManager.createQuery("from PaymentType", PaymentType.class).getResultList();
	}

	@Override
	public PaymentType find(Long id) {
		return entityManager.find(PaymentType.class, id);
	}

	@Transactional
	@Override
	public PaymentType persist(PaymentType paymentType) {
		return entityManager.merge(paymentType);
	}

	@Transactional
	@Override
	public void remove(PaymentType paymentType) {
		paymentType = find(paymentType.getId());
		entityManager.remove(paymentType);
	}

}
