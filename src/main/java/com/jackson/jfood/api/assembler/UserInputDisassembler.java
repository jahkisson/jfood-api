package com.jackson.jfood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jackson.jfood.api.model.input.UserAddInput;
import com.jackson.jfood.api.model.input.UserUpdateInput;
import com.jackson.jfood.domain.model.User;

@Component
public class UserInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public User toDomainObject(UserAddInput userInput) {
		return modelMapper.map(userInput, User.class);
	}
	
	public User toDomainObject(UserUpdateInput userInput) {
		return modelMapper.map(userInput, User.class);
	}
	
	public void copyToDomainObject(UserAddInput userInput, User user) {
		modelMapper.map(userInput, user);
	}
	
	public void copyToDomainObject(UserUpdateInput userInput, User user) {
		modelMapper.map(userInput, user);
	}
}
