package com.jackson.jfood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jackson.jfood.api.assembler.PaymentTypeInputDisassembler;
import com.jackson.jfood.api.assembler.PaymentTypeModelAssembler;
import com.jackson.jfood.api.model.PaymentTypeModel;
import com.jackson.jfood.api.model.input.PaymentTypeInput;
import com.jackson.jfood.domain.model.PaymentType;
import com.jackson.jfood.domain.repository.PaymentTypeRepository;
import com.jackson.jfood.domain.service.PaymentTypeRegistrationService;

@RestController
@RequestMapping(value = "/paymenttypes")
public class PaymentTypeController {

	@Autowired
	private PaymentTypeRepository paymentTypeRepository;
	
	@Autowired
	private PaymentTypeRegistrationService paymentTypeRegistration;
	
	@Autowired
	private PaymentTypeInputDisassembler paymentTypeInputDisassembler;
	
	@Autowired
	private PaymentTypeModelAssembler paymentTypeModelAssembler;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PaymentTypeModel> listar() {
		return paymentTypeModelAssembler.toCollectionModel(paymentTypeRepository.findAll());
	}
	
	@GetMapping("/{paymentTypeId}")
	public PaymentTypeModel getById(@PathVariable Long paymentTypeId) {
		return paymentTypeModelAssembler.toModel(paymentTypeRegistration.findByIdOrFail(paymentTypeId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PaymentTypeModel add(@RequestBody @Valid PaymentTypeInput paymentTypeInput) {
		PaymentType paymentType = paymentTypeInputDisassembler.toDomainObject(paymentTypeInput);
		return paymentTypeModelAssembler.toModel(paymentTypeRegistration.save(paymentType));
	}
	
	@PutMapping("/{paymentTypeId}")
	public PaymentTypeModel update(@PathVariable Long paymentTypeId, @RequestBody @Valid PaymentTypeInput paymentTypeInput) {
		PaymentType paymentTypeToUpdate = paymentTypeRegistration.findByIdOrFail(paymentTypeId);
		paymentTypeInputDisassembler.copyToDomainObject(paymentTypeInput, paymentTypeToUpdate);
		return paymentTypeModelAssembler.toModel(paymentTypeRegistration.save(paymentTypeToUpdate));
	}
	
	@DeleteMapping("/{paymentTypeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long paymentTypeId) {
		paymentTypeRegistration.remove(paymentTypeId);
	}
}