package com.jackson.jfood.domain.exception;

public class RoleNotFoundException extends EntityNotFoundException {
	
	private static final long serialVersionUID = 1L;
	
	public RoleNotFoundException(String message) {
		super(message);
	}

	public RoleNotFoundException(Long roleId) {
		this(String.format("O grupo de código %d não foi encontrado", roleId));
	}
}
