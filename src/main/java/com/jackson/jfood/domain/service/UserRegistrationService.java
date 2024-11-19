package com.jackson.jfood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jackson.jfood.domain.exception.BusinessException;
import com.jackson.jfood.domain.exception.EntityIsBeingUsedException;
import com.jackson.jfood.domain.exception.UserNotFoundException;
import com.jackson.jfood.domain.model.Role;
import com.jackson.jfood.domain.model.User;
import com.jackson.jfood.domain.repository.UserRepository;

@Service
public class UserRegistrationService {

	private final String MESSAGE_USER_IN_USE = "O usuário de código %d está em uso";
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRegistrationService roleRegistrationService;
	
	public User findOrFail(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(userId));
	}
	
	@Transactional
	public User save(User user) {
		userRepository.detach(user);
		
		Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
		if (existingUser.isPresent() && !existingUser.get().getId().equals(user.getId())) {
			throw new BusinessException(String.format("Já existe um usuário cadastrado com o email %s", user.getEmail()));
		}
		
		return userRepository.save(user);
	}
	
	@Transactional
	public void remove(Long id) {
		try {
			userRepository.deleteById(id);
			userRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new UserNotFoundException(id);
		} catch (DataIntegrityViolationException ex) {
			throw new EntityIsBeingUsedException(String.format(MESSAGE_USER_IN_USE, id));
		}
	}

	@Transactional
	public void changePassword(Long userId, String oldPassword, String newPassword) {
		User user = findOrFail(userId);
		if (user.passwordDoesNotMatch(oldPassword))
			throw new BusinessException("Senha antiga não confere");
		
		user.setPassword(newPassword);
	}
	
	@Transactional
	public void setRole(Long userId, Long roleId) {
		User user = findOrFail(userId);
		Role role = roleRegistrationService.findOrFail(roleId);
		user.addRole(role);
	}
	
	@Transactional
	public void unsetRole(Long userId, Long roleId) {
		User user = findOrFail(userId);
		Role role = roleRegistrationService.findOrFail(roleId);
		user.removeRole(role);
	}
}
