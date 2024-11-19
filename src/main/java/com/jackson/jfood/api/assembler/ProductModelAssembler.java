package com.jackson.jfood.api.assembler;

import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jackson.jfood.api.model.ProductModel;
import com.jackson.jfood.domain.model.Product;

@Component
public class ProductModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public ProductModel toModel(Product product) {
		return modelMapper.map(product, ProductModel.class);
	}
	
	public List<ProductModel> toCollectionModel(Set<Product> products) {
		return products.stream().map(x -> toModel(x)).toList();
	}
}
