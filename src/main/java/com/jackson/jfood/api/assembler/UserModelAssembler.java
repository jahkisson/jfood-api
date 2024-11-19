package com.jackson.jfood.api.assembler;

import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jackson.jfood.api.model.UserModel;
import com.jackson.jfood.domain.model.User;

@Component
public class UserModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public UserModel toModel(User user) {
		return modelMapper.map(user, UserModel.class);
	}
	
	public List<UserModel> toCollectionModel(Collection<User> users) {
		return users.stream().map(x -> toModel(x)).toList();
	}
}
