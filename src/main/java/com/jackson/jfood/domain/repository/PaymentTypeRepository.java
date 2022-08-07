package com.jackson.jfood.domain.repository;

import java.util.List;

import com.jackson.jfood.domain.model.PaymentType;

public interface PaymentTypeRepository {

	List<PaymentType> list();
	PaymentType find(Long id);
	PaymentType persist(PaymentType paymentType);
	void remove(PaymentType paymentType);
}
