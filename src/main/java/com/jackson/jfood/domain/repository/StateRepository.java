package com.jackson.jfood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jackson.jfood.domain.model.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

}
