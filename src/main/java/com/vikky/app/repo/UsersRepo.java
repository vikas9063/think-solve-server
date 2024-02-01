package com.vikky.app.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vikky.app.entity.UserEntity;

public interface UsersRepo extends JpaRepository<UserEntity, String> {

	Optional<UserEntity> findByUserEmail(String email);
	
}
