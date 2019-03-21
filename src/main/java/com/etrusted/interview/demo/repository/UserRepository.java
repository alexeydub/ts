package com.etrusted.interview.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.etrusted.interview.demo.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
  Optional<User> findByEmail(String email);
}