package com.jackson.jfood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jackson.jfood.api.assembler.UserInputDisassembler;
import com.jackson.jfood.api.assembler.UserModelAssembler;
import com.jackson.jfood.api.model.UserModel;
import com.jackson.jfood.api.model.input.UserAddInput;
import com.jackson.jfood.api.model.input.UserChangePasswordInput;
import com.jackson.jfood.api.model.input.UserUpdateInput;
import com.jackson.jfood.domain.model.User;
import com.jackson.jfood.domain.repository.UserRepository;
import com.jackson.jfood.domain.service.UserRegistrationService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRegistrationService userRegistration;
	
	@Autowired
	private UserInputDisassembler userInputDisassembler;
	
	@Autowired
	private UserModelAssembler userModelAssembler;
	
	@GetMapping
	public List<UserModel> listAll() {
		return userModelAssembler.toCollectionModel(userRepository.findAll());
	}
	
	@GetMapping("/{userId}")
	public UserModel getById(@PathVariable Long userId) {
		return userModelAssembler.toModel(userRegistration.findOrFail(userId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserModel add(@RequestBody @Valid UserAddInput userInput) {
		User user = userInputDisassembler.toDomainObject(userInput);
		return userModelAssembler.toModel(userRegistration.save(user));
	}
	
	@PutMapping("/{userId}")
	public UserModel update(@PathVariable("userId") Long id, @RequestBody @Valid UserUpdateInput userInput) {
		User userToUpdate = userRegistration.findOrFail(id);
		userInputDisassembler.copyToDomainObject(userInput, userToUpdate);
		return userModelAssembler.toModel(userRegistration.save(userToUpdate));
	}
	
	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long userId) {
		userRegistration.remove(userId);
	}
	
	@PutMapping("/{userId}/password")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void changePassword(@PathVariable("userId") Long id, @RequestBody @Valid UserChangePasswordInput userInput) {
		userRegistration.changePassword(id, userInput.getOldPassword(), userInput.getNewPassword());
	}
}
