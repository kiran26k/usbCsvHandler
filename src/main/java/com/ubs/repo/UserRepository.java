package com.ubs.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ubs.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByPrimaryKey(Long key);

	Long deleteByPrimaryKey(Long key);

}
