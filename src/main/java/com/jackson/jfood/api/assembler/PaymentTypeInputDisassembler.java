package com.jackson.jfood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jackson.jfood.api.model.input.PaymentTypeInput;
import com.jackson.jfood.domain.model.PaymentType;

@Component
public class PaymentTypeInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public PaymentType toDomainObject(PaymentTypeInput paymentTypeInput) {
		return modelMapper.map(paymentTypeInput, PaymentType.class);
	}
	
	public void copyToDomainObject(PaymentTypeInput paymentTypeInput, PaymentType paymentType) {
		modelMapper.map(paymentTypeInput, paymentType);
	}
}
