package com.jackson.jfood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jackson.jfood.api.assembler.ProductInputDisassembler;
import com.jackson.jfood.api.assembler.ProductModelAssembler;
import com.jackson.jfood.api.model.ProductModel;
import com.jackson.jfood.api.model.input.ProductInput;
import com.jackson.jfood.domain.model.Product;
import com.jackson.jfood.domain.model.Restaurant;
import com.jackson.jfood.domain.service.ProductRegistrationService;
import com.jackson.jfood.domain.service.RestaurantRegistrationService;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products")
public class RestaurantProductController {

	@Autowired
	private RestaurantRegistrationService restaurantService;
	
	@Autowired
	private ProductModelAssembler productModelAssembler;
	
	@Autowired
	private ProductRegistrationService productRegistrationService;
	
	@Autowired
	private ProductInputDisassembler productInputDisassembler;
	
	@GetMapping
	public List<ProductModel> listar(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantService.findByIdOrFail(restaurantId);
		return productModelAssembler.toCollectionModel(restaurant.getProducts());
	}
	
	@GetMapping("/{productId}")
	public ProductModel getById(@PathVariable Long restaurantId, @PathVariable Long productId) {
		Product product = productRegistrationService.findOrFailByRestaurantAndProduct(restaurantId, productId);
		return productModelAssembler.toModel(product);
	}
	
	@PostMapping
	public ProductModel add(@PathVariable Long restaurantId, @RequestBody @Valid ProductInput productInput) {
		Product product = productInputDisassembler.toDomainObject(productInput);
		product = productRegistrationService.saveProduct(restaurantId, product);
		return productModelAssembler.toModel(product);
	}
	
	@PutMapping("/{productId}")
	public ProductModel update(@PathVariable Long restaurantId, @PathVariable Long productId, 
			@RequestBody @Valid ProductInput productInput) {
		Product product = productRegistrationService.findOrFailByRestaurantAndProduct(restaurantId, productId);
		productInputDisassembler.copyToDomainObject(productInput, product);
		product = productRegistrationService.saveProduct(restaurantId, product);
		return productModelAssembler.toModel(product);
	}
}
