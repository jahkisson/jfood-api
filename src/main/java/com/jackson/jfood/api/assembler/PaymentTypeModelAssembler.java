package com.jackson.jfood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jackson.jfood.api.model.PaymentTypeModel;
import com.jackson.jfood.domain.model.PaymentType;

@Component
public class PaymentTypeModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public PaymentTypeModel toModel(PaymentType paymentType) {
		return modelMapper.map(paymentType, PaymentTypeModel.class);
	}
	
	public List<PaymentTypeModel> toCollectionModel(Collection<PaymentType> list) {
		return list.stream().map(x -> toModel(x)).collect(Collectors.toList());
	}
	
}
