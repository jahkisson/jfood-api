package com.jackson.jfood.domain.model;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class User {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	@NotBlank
	private String name;
	
	@Column(nullable = false)
	@NotBlank
	
	@Email
	private String email;
	
	@Column(nullable = false)
	@NotBlank
	private String password;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime creationDate;
	
	@ManyToMany
	@JoinTable(name = "user_role",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<Role>();

	public boolean passwordDoesNotMatch(String password) {
		return !this.passwordMatches(password);
	}

	private boolean passwordMatches(String password) {
		return this.getPassword().equals(password);
	}
	
	public boolean addRole(Role role) {
		return getRoles().add(role);
	}
	
	public boolean removeRole(Role role) {
		return getRoles().remove(role);
	}
}
