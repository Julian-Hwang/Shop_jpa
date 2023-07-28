package com.julian5383.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.julian5383.entity.User;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
}
